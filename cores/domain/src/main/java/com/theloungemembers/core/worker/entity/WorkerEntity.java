package com.theloungemembers.core.worker.entity;

import java.time.OffsetDateTime;

import com.theloungemembers.core.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker")
public class WorkerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column
    private String workerId;

    @Column
    private String workerName;

    @Column
    private String password;

    @Column
    private OffsetDateTime passwordResetDate;

    @Column
    private String passwordUpload;

    @Column
    private String phoneNum;

    @Column
    private String emailAddress;

    @Column
    private String defaultMenuCode;

    @Column
    private String menuAuth;

    @Column
    private String serviceOperator;

    @Column
    private Integer workerMaxIssueCount;

    @Column
    private String saleChannel;

    @Column
    private String autoLogin;

    @Column
    private String allowOnlyOneLogin;

    @Column
    private String memo;

    @Column
    private Short menuAllAuth;
}