package com.theloungemembers.core.worker.entity;

import com.theloungemembers.core.common.entity.BaseEntity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

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
 * SMS 발송코드 위치
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker_sms_concern_page")
public class WorkerSmsConcernPageEntity extends BaseEntity {

    /**
     * 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    /**
     * 코드
     */
    @Column(name = "code")
    private String code;

    /**
     * 카테고리
     */
    @Column(name = "category")
    private String category;

    /**
     * JSON 데이터
     */
    @Column(name = "data")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> data;

    /**
     * 제목
     */
    @Column(name = "title")
    private String title;

    /**
     * 소스위치
     */
    @Column(name = "source_position")
    private String sourcePosition;

    /**
     * 메모
     */
    @Column(name = "memo")
    private String memo;

}
