package com.rodait.boardservice.service;

import com.rodait.boardservice.client.PointClient;
import com.rodait.boardservice.client.UserClient;
import com.rodait.boardservice.domain.Board;
import com.rodait.boardservice.dto.BoardResponseDto;
import com.rodait.boardservice.dto.CreateBoardRequestDto;
import com.rodait.boardservice.domain.BoardRepository;
import com.rodait.boardservice.dto.UserDto;
import com.rodait.boardservice.dto.UserResponsedto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserClient userClient;
    private final PointClient pointClient;

    public BoardService(BoardRepository boardRepository, UserClient userClient, PointClient pointClient) {
        this.userClient = userClient;
        this.boardRepository = boardRepository;
        this.pointClient = pointClient;
    }

    /**
     * 게시글 작성
     * @param requestDto
     */
    public void createBoard(CreateBoardRequestDto requestDto) {

        // 게시글 저장 플래그
        boolean isBoardCreated = false;
        Long savedBoardId = null;

        // 포인트 차감 플래그
        boolean isPointDeducted = false;

        try {
            // 게시글 작성 시 포인트 100점 차감
            pointClient.deductPoints(requestDto.getUserId(), 100);
            isPointDeducted = true; // 포인트가 정상 차감됨
            System.out.println("포인트 100점 차감 완료");

            var board = new Board(
                    requestDto.getTitle(),
                    requestDto.getContent(),
                    requestDto.getUserId()
            );
            Board save = boardRepository.save(board);
            savedBoardId = save.getId();
            isBoardCreated = true; // 게시글이 정상 저장됨
            System.out.println("게시글 저장 완료");

            // 게시글 작성 시 활동 점수 10점 추가
            // 여기에 대한 롤백은 필요없음
            userClient.addActivityScore(save.getUserId(), 10);
            System.out.println("활동 점수 10점 추가 완료");
        }catch (Exception e){

            if(isBoardCreated){
                // 게시글이 저장된 상태에서 예외 발생 시, 저장된 게시글 삭제
                boardRepository.deleteById(savedBoardId);
                System.out.println("[보상트랜젝션]" + savedBoardId + "번 게시글 삭제 완료");
            }

            if(isPointDeducted){
                // 포인트가 차감된 상태에서 예외 발생 시, 차감된 포인트 복구
                pointClient.addPoints(requestDto.getUserId(), 100);
                System.out.println("[보상트랜젝션] 포인트 100점 복구 완료");
            }

            // 실패 응단처리하기 위해 예외 던지기
            throw e;

        }
    }

    /**
     * 게시글 단건 조회
     * @param boardId
     * @return
     */
    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        Optional<UserResponsedto> optionalUserResponsedto = userClient.getFetchUser(board.getUserId());

        UserDto userDto = null;
        if(optionalUserResponsedto.isPresent()){
            UserResponsedto userResponseDto = optionalUserResponsedto.get();
            userDto = new UserDto(
                    userResponseDto.getUserId(),
                    userResponseDto.getEmail()
            );
        }

        BoardResponseDto boardResponseDto = new BoardResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                userDto
        );

        return boardResponseDto;
    }

    /**
     * 모든 게시글 조회
     * @return
     */
    public List<BoardResponseDto> getBoards() {
        List<Board> boards = boardRepository.findAll();

        List<Long> userIds = boards.stream()
                .map(Board::getUserId)
                .distinct()
                .toList();

        List<UserResponsedto> userResponsedtos = userClient.getFetchAllUsers(userIds);

        Map<Long, UserDto> userMap = new HashMap<>();

        for (UserResponsedto userResponsedto : userResponsedtos) {
            UserDto userDto = new UserDto(
                    userResponsedto.getUserId(),
                    userResponsedto.getEmail()
            );
            userMap.put(userDto.getUserId(), userDto);
        }

        return boards.stream()
                .map(board -> new BoardResponseDto(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        userMap.get(board.getUserId())
                ))
                .toList();

    }
}
