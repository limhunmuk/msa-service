package com.rodait.userservice.controller;

import com.rodait.userservice.dto.AddActivityScoreRequestDto;
import com.rodait.userservice.dto.SignUpRequestDto;
import com.rodait.userservice.dto.UserResponseDto;
import com.rodait.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/users")
public class UserInternalController {

    private final UserService userService;

    public UserInternalController(UserService userService) {
        this.userService = userService;
    }

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
}
