package com.rodait.userservice.dto.userhistory;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginHistoryRequestDto {

    private Long userId;
    private String ipAddress;

    public LoginHistoryRequestDto(Long userId, String ipAddress) {
        this.userId = userId;
        this.ipAddress = ipAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
