package com.rodait.userservice.service;

import com.rodait.userservice.client.PointClient;
import com.rodait.userservice.dto.AddActivityScoreRequestDto;
import com.rodait.userservice.domian.User;
import com.rodait.userservice.dto.SignUpRequestDto;
import com.rodait.userservice.domian.UserRepository;
import com.rodait.userservice.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointClient pointClient;

    public UserService(UserRepository userRepository, PointClient pointClient) {
        this.pointClient = pointClient;
        this.userRepository = userRepository;
    }

    @Transactional
    public void signUp(SignUpRequestDto requestDto) {
        var user = new com.rodait.userservice.domian.User(
                null,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        User save = userRepository.save(user);
        // 회원가입시 포인트 1000점 지급
        pointClient.addPoints(save.getId(), 1000);
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 업습니다."));
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public List<UserResponseDto> getUsersByIds(List<Long> userIds) {

        List<User> users = userRepository.findAllById(userIds);
        return users.stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();

    }

    @Transactional
    public void addActivityScore(AddActivityScoreRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 업습니다."));

        user.addActivityScore(requestDto.getScore());
        userRepository.save(user);
    }
}
