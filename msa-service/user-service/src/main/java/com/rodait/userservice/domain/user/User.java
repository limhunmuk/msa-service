package com.rodait.userservice.domain.user;
import com.rodait.userservice.domain.BaseEntity;
import com.rodait.userservice.domain.userloginhistory.UserLoginHistory;
import com.rodait.userservice.domain.userprofile.UserProfile;
import com.rodait.userservice.domain.usertoken.UserToken;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Integer activityScore;

    @Column(nullable = false, length = 10)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLoginHistory> loginHistories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserToken> tokens;

}