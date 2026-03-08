package com.rodait.userservice.service;


import com.rodait.userservice.domain.user.User;
import com.rodait.userservice.domain.user.UserRepository;
import com.rodait.userservice.domain.userloginhistory.UserLoginHistory;
import com.rodait.userservice.domain.userloginhistory.UserLoginHistoryRepository;
import com.rodait.userservice.dto.userhistory.LoginHistoryRequestDto;
import com.rodait.userservice.dto.userhistory.LoginHistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginHistoryService {

    private final UserRepository userRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

    /**
     * 로그인 기록 저장
     * @param userId
     * @param ipAddress
     */
    @Transactional
    public void saveLoginHistory(Long userId, String ipAddress) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        var loginHistory = UserLoginHistory.builder()
                .user(user)
                .ipAddress(ipAddress)
                .loginAt(java.time.LocalDateTime.now())
                .build();

        userLoginHistoryRepository.save(loginHistory);
    }


    /**
     * 로그인 기록 조회
     * @param userId
     * @return
     */
    public List<LoginHistoryResponseDto> getLoginHistoryByUserId(Long userId) {
        List<UserLoginHistory> loginHistories = userLoginHistoryRepository.findUserLoginHistoriesByUserId(userId);

        List<LoginHistoryResponseDto> responseDtos = new ArrayList<>();
        for (UserLoginHistory loginHistory : loginHistories) {
            var loginHistoryRequestDto = LoginHistoryResponseDto.builder()
                    .ipAddress(loginHistory.getIpAddress())
                    .loginAt(loginHistory.getLoginAt())
                    .build();
            responseDtos.add(loginHistoryRequestDto);
        }

        return responseDtos;

    }


}
