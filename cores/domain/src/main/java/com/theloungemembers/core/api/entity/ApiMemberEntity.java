package com.theloungemembers.core.api.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.theloungemembers.core.common.entity.BaseEntity;

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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_member")
public class ApiMemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column
    private String accountId;

    @Column
    private String companyName;

    @Column
    private String serviceCode;

    @Column
    private String password;

    @Column
    private OffsetDateTime passwordResetDate;

    @Column
    private String freepassIp;

    @Column
    private String allowMsgNotify;

    @Column
    private String loungeCode;

    @Column
    private String loungeName;

    @Column
    private Integer loungeUid;

    @Column
    private String userName;

    @Column
    private String phoneNum;

    @Column
    private String emailAddress;

    @Column
    private Short isEncrypted;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column
    private String encData;

    @Column
    private String memo;
}