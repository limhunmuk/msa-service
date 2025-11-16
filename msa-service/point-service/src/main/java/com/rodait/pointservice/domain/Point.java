package com.rodait.pointservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer points;

    public Point(Long userId, Integer points) {
        this.userId = userId;
        this.points = points;
    }

    public Point() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void addAmount(Integer amount) {
        this.points += amount;
    }

    public void deductAmount(Integer amount) {
        this.points -= amount;
    }
}
