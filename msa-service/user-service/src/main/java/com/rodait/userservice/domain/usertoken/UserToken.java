package com.rodait.userservice.domain.usertoken;

import com.rodait.userservice.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTokenId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 255)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

}
