package com.rodait.boardservice.consumer;

import com.rodait.boardservice.domain.User;
import com.rodait.boardservice.dto.SaveUserRequestDto;
import com.rodait.boardservice.event.UserSignedUpEvent;
import com.rodait.boardservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserSignedEventConsumer {

    private final UserService userService;

    public UserSignedEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "user-signed-up",
            groupId = "board-service"
    )
    public void consume(String message) {

        UserSignedUpEvent userSignedUpEvent = UserSignedUpEvent.fromJson(message);

        SaveUserRequestDto saveUserRequestDto = new SaveUserRequestDto(
                userSignedUpEvent.getUserId(),
                userSignedUpEvent.getName()
        );

        userService.save(saveUserRequestDto);

    }

}
