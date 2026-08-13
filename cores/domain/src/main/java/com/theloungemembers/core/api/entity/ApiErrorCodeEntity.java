package com.theloungemembers.core.api.entity;

import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_error_code")
public class ApiErrorCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "error_code", nullable = false)
    private String errorCode;

    @Column(name = "error_meaning", nullable = false)
    private String errorMeaning;

    @Column(name = "memo")
    private String memo;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private OffsetDateTime regDate;

}