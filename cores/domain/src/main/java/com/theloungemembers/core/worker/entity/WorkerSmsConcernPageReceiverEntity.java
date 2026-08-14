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
 * SMS 관계
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker_sms_concern_page_receiver")
public class WorkerSmsConcernPageReceiverEntity extends BaseEntity {

    /**
     * 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * 발송코드
     */
    @Column(name = "concern_page_code")
    private String concernPageCode;

    /**
     * 수신자 ID
     */
    @Column(name = "receiver_id")
    private String receiverId;

}