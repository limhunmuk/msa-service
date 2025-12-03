package com.rodait.userservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BoardCreatedEvent {

    private Long userId;

    public BoardCreatedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    public static BoardCreatedEvent fromJson(String json) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, BoardCreatedEvent.class);
        }catch (Exception e){
            throw new RuntimeException("json 파싱 실패", e);
        }
    }
}
