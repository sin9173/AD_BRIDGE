package com.adbridge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt; //생성일시

    @LastModifiedDate
    private LocalDateTime modifiedAt; //수정일시

    private LocalDateTime deletedAt; //삭제일시

    @ColumnDefault("0")
    @Column(columnDefinition = "TINYINT")
    private Boolean deleteYn = false; //삭제여부


    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.deleteYn = true;
    }
}
