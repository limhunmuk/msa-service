package com.rodait.userservice.domian.userprofile;

import com.rodait.userservice.domian.BaseDateEntity;
import com.rodait.userservice.domian.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "user_profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserProfile extends BaseDateEntity {

    @Id
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