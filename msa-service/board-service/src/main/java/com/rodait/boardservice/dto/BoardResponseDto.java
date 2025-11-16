package com.rodait.boardservice.dto;

public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private UserDto userDto;

    public BoardResponseDto(Long boardId, String title, String content, UserDto userDto) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserDto getUserDto() {
        return userDto;
    }

}
