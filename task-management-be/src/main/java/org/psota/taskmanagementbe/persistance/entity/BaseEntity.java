package org.psota.taskmanagementbe.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> {
    @Column(nullable = false)
    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @Column(nullable = false)
    @LastModifiedBy
    private ID updatedBy;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private OffsetDateTime createdAt;

    @Column(updatable = false, nullable = false)
    @CreatedBy
    private ID createdBy;
}
