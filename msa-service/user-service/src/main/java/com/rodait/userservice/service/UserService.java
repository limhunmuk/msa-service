package com.rodait.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodait.userservice.client.PointClient;
import com.rodait.userservice.dto.AddActivityScoreRequestDto;
import com.rodait.userservice.domian.User;
import com.rodait.userservice.dto.SignUpRequestDto;
import com.rodait.userservice.domian.UserRepository;
import com.rodait.userservice.dto.UserResponseDto;
import com.rodait.userservice.event.UserSignedUpEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointClient pointClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserService(UserRepository userRepository
            , PointClient pointClient
            , KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.pointClient = pointClient;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 회원가입
     * @param requestDto
     */
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

        // 회원가입 완료 이벤트
        UserSignedUpEvent userSignedUpEvent = new UserSignedUpEvent(
                save.getId(),
                save.getName()
        );
        // 이벤트 발행 로직 필요 (예: Kafka, RabbitMQ 등)
        // kafka 로 보내려면 string 형태로 보내야됨
        kafkaTemplate.send(
            "user-signed-up"
            , toJsonString(userSignedUpEvent)
        );

    }

    private String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
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

    /**
     * 여러 사용자 조회
     * @param userIds
     * @return
     */
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

    /**
     * 활동 점수 추가
     * @param requestDto
     */
    @Transactional
    public void addActivityScore(AddActivityScoreRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 업습니다."));

        user.addActivityScore(requestDto.getScore());
        userRepository.save(user);
    }
}
