package com.rodait.boardservice.client;

import com.rodait.boardservice.dto.DeductPointRequestDto;
import com.rodait.boardservice.dto.UserResponsedto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Component
public class PointClient {

    private final RestClient restClient;

    public PointClient(@Value("${client.point-service.url}") String userServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    public void deductPoints(Long userId, int amount) {

        DeductPointRequestDto requestDto = new DeductPointRequestDto(userId, amount);

        // REST API 호출
        this.restClient.post()
                .uri("/points/deduct", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestDto)
                .retrieve()
                .toBodilessEntity(); // 응답으로 받을게 없다
    }



}
