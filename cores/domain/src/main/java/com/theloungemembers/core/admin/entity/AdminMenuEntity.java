package com.theloungemembers.core.admin.entity;

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
@Table(name = "admin_menu")
public class AdminMenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column
    private String code;

    @Column
    private String menuCode;

    @Column
    private String linkUrl;

    @Column
    private String title;

    @Column
    private String color;

    @Column
    private String topMargin;

    @Column
    private Integer displayOrdinal;

    @Column
    private Integer bookmarkDisplayOrdinal;
}