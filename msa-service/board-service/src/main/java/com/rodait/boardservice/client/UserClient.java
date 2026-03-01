package com.rodait.boardservice.client;

import com.rodait.boardservice.dto.AddActivityScoreRequestDto;
import com.rodait.boardservice.dto.UserResponsedto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(@Value("${client.user-service.url}") String userServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    public Optional<UserResponsedto> getFetchUser(Long userId) {

        // REST API 호출
        try {
            UserResponsedto body = this.restClient.get()
                    .uri("/internal/users/{userId}", userId)
                    .retrieve()
                    .body(UserResponsedto.class);
            return Optional.ofNullable(body);
        } catch (RestClientException e) {

            // 예외 발생시 로깅 처리 해야됨
            // 예외 처리
            return Optional.empty();
        }

    }

    public List<UserResponsedto> getFetchAllUsers(List<Long> userIds) {

        try {
            return this.restClient.get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path("/internal/users")
                                    .queryParam("userIds", userIds)
                                    .build()
                    )
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        }catch (RestClientException e){
            // 예외 발생시 로깅 처리 해야됨

            return List.of();
        }
    }

    // 활동점수 적립
    public void addActivityScore(Long userId, int score) {

        AddActivityScoreRequestDto requestDto = new AddActivityScoreRequestDto(userId, score);
        this.restClient.post()
                .uri("/internal/users/activity-score/add", userId)
                .body(requestDto)
                .retrieve()
                .toBodilessEntity();
    }
}
