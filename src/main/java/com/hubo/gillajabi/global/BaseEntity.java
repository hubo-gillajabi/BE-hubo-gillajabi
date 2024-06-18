package com.hubo.gillajabi.global;

import com.hubo.gillajabi.global.type.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.hubo.gillajabi.global.type.StatusType.*;
import static jakarta.persistence.EnumType.STRING;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedTime;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private StatusType status = ENABLE;

    protected BaseEntity(final StatusType status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return this.status.equals(DELETED);
    }

    public void changeStatusToDeleted() {
        this.status = DELETED;
    }

    public void changeStatusToEnable() {
        this.status = ENABLE;
    }

    public void changeStatusToDisable() {
        this.status = DISABLE;
    }
}


