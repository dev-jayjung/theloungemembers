package com.theloungemembers.core.common.entity;

import com.theloungemembers.core.type.ServiceStatus;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    //  @CreatedBy
    //  @Column(updatable = false)
    //  private Long sysRegNo;

    //    @LastModifiedBy
    //    @Column
    //    private Long sysUpdNo;

    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime regDate;

    @LastModifiedDate
    @Column
    private OffsetDateTime updateDate;

    @Column
    private ServiceStatus onService;

    public void delete() {
        this.onService = ServiceStatus.STOPPED;
    }


}