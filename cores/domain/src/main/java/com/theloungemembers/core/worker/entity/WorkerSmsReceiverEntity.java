package com.theloungemembers.core.worker.entity;

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

/**
 * SMS 수신자
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker_sms_receiver")
public class WorkerSmsReceiverEntity extends BaseEntity {

    /**
     * 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * 사용자 ID
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * 사용자 이름
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 핸드폰
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 이메일주소
     */
    @Column(name = "email_address")
    private String emailAddress;

    /**
     * 역할
     */
    @Column(name = "role")
    private String role;

}