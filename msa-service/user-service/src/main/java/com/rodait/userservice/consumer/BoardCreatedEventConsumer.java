package com.rodait.userservice.consumer;

import com.rodait.userservice.dto.AddActivityScoreRequestDto;
import com.rodait.userservice.event.BoardCreatedEvent;
import com.rodait.userservice.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BoardCreatedEventConsumer {


    private UserService userService;

    public BoardCreatedEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(
            topics = "board-created",
            groupId = "user-service"
    )
    public void consume(BoardCreatedEvent boardCreatedEvent){

        BoardCreatedEvent event = BoardCreatedEvent.fromJson(boardCreatedEvent.toString());

        // 이벤트 처리 로직 호출
        // 게시글 생성 ㅣ 활동점수 10점추가

        AddActivityScoreRequestDto addActivityScoreRequestDto = new AddActivityScoreRequestDto(
                event.getUserId(),
                10
        );
        userService.addActivityScore(addActivityScoreRequestDto);

        System.out.printf("활동점수 적립 안료 - userId: %d, score: %d%n",
                event.getUserId(),
                10
        );
    }
}
