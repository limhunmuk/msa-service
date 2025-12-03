package com.rodait.boardservice.event;

public class BoardCreatedEvent {
    private Long boardId;

    public BoardCreatedEvent(Long boardId) {
        this.boardId = boardId;
    }

    public Long getBoardId() {
        return boardId;
    }

}
