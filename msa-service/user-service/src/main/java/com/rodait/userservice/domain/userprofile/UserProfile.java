package com.rodait.userservice.domain.userprofile;

import com.rodait.userservice.domain.BaseEntity;
import com.rodait.userservice.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProfileId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 255)
    private String profileImageUrl;

    @Column(nullable = false, length = 255)
    private String selfIntro;

    @Column(nullable = false, length = 12)
    private String phoneNo;

    @Column(nullable = false)
    private LocalDate birthDate;

}