package com.rodait.boardservice.controller;

import com.rodait.boardservice.dto.BoardResponseDto;
import com.rodait.boardservice.dto.CreateBoardRequestDto;
import com.rodait.boardservice.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody CreateBoardRequestDto requestDto) {
        boardService.createBoard(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardById(@PathVariable Long boardId) {
        //BoardResponseDto boardResponseDto = boardService.getBoard(boardId);
        BoardResponseDto boardResponseDto = boardService.getBoard2(boardId);
        return ResponseEntity.ok(boardResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        //List<BoardResponseDto> boardResponseDtos = boardService.getBoards();
        List<BoardResponseDto> boardResponseDtos = boardService.getBoards2();
        return ResponseEntity.ok(boardResponseDtos);
    }

}
