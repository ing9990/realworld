package com.realworldbackend.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StatusType status = StatusType.USING;

    protected BaseEntity(final StatusType status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return this.status.equals(StatusType.DELETED);
    }

    public void changeStatusToDeleted() {
        this.status = StatusType.DELETED;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt.atOffset(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt.atOffset(ZoneOffset.UTC)
                .toLocalDateTime();
    }
}