package com.rodait.boardservice.service;

import com.rodait.boardservice.domain.User;
import com.rodait.boardservice.domain.UserRepository;
import com.rodait.boardservice.dto.SaveUserRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(SaveUserRequestDto saveUserRequestDto) {
        var user = new User(
                saveUserRequestDto.getUserId(),
                saveUserRequestDto.getName()
        );
        userRepository.save(user);
    }
}
