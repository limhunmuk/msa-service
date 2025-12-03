package com.rodait.boardservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserSignedUpEvent {

    private Long userId;
    private String name;

    public UserSignedUpEvent() {}

    public static UserSignedUpEvent fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, UserSignedUpEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("json 파싱 실패", e);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

}
