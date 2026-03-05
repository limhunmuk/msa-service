package com.rodait.userservice.domian;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDateEntity extends BaseCreatedEntity{

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

}