package com.example.apigatewayservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.out.println("token = " + token);

            //토큰일 없는 경우
            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // JWT 토큰 검증 로직 추가 (생략)

            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

            String subject = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();

            System.out.println("user id = " + subject);

            // 검증이 완료된 경우 요청을 다음 필터로 전달


            return chain.filter(
                    exchange.mutate()
                            .request(exchange.getRequest().mutate()
                                    .header("X-User-Id", subject)
                                    .build())
                            .build()
            );


        };

    }
}
