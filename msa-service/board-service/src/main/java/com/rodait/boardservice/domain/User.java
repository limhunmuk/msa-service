package com.rodait.boardservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private Long userId;

    private String name;

    public User() {}

    public User(Long id, String name) {
        this.userId = id;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }

}
