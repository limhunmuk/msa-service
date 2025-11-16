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

    @Transactional
    public void createBoard(CreateBoardRequestDto requestDto) {
        
        // 게시글 작성 시 포인트 100점 차감
        pointClient.deductPoints(requestDto.getUserId(), 100);

        var board = new Board(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getUserId()
        );
        Board save = boardRepository.save(board);

        // 게시글 작성 시 활동 점수 10점 추가
        userClient.addActivityScore(save.getUserId(), 10);
    }

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
