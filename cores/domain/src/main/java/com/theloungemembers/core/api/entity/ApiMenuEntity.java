package com.theloungemembers.core.api.entity;

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
@Table(name = "api_menu")
public class ApiMenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column
    private String groupCode;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private Integer displayOrdinal;

    @Column
    private String linkUrl;

    @Column
    private String memo;


}