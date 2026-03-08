package com.rodait.userservice.dto.userhistory;

import com.rodait.userservice.dto.user.LoginResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryResponseDto {

    private Long userId;
    private String ipAddress;
    private LocalDateTime loginAt;

}
