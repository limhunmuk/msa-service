package com.rodait.userservice.controller;

import com.rodait.userservice.dto.user.AddActivityScoreRequestDto;
import com.rodait.userservice.dto.user.UserResponseDto;
import com.rodait.userservice.dto.userhistory.LoginHistoryRequestDto;
import com.rodait.userservice.service.UserLoginHistoryService;
import com.rodait.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserInternalController {

    private final UserService userService;
    private final UserLoginHistoryService userLoginHistoryService;

    @GetMapping("{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userService.getUser(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam List<Long> userIds) {
        List<UserResponseDto> users = userService.getUsersByIds(userIds);
        return ResponseEntity.ok(users);
    }

    @PostMapping("activity-score/add")
    public ResponseEntity<Void> addActivityScore(
            @RequestBody AddActivityScoreRequestDto requestDto) {

        userService.addActivityScore(requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/login-history")
    public void createLoginHistory(
            @PathVariable Long userId,
            @RequestBody LoginHistoryRequestDto requestDto) {

        userLoginHistoryService.saveLoginHistory(userId, requestDto.getIpAddress());
    }

    @GetMapping("/{userId}/login-history")
    public ResponseEntity<List<LoginHistoryRequestDto>> getLoginHistory(@PathVariable Long userId) {
        List<LoginHistoryRequestDto> loginHistory = userLoginHistoryService.getLoginHistoryByUserId(userId);
        return ResponseEntity.ok(loginHistory);
    }
}
