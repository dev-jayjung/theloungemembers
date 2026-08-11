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
    private Long uid;

    @Column
    private Integer level;

    @Column
    private String parentCode;

    @Column
    private String menuCode;

    @Column
    private String authCode;

    @Column
    private String tabCode;

    @Column
    private String tabName;

    @Column
    private String tabTooltip;

    @Column
    private Integer displayOrdinal;

    @Column
    private String displayInMenu;

    @Column
    private String commonWorkDomain;

    @Column
    private String defaultPermission;

    @Column
    private String linkUrl;

    @Column
    private String title;

    @Column
    private String title2;

    @Column
    private String color;

    @Column
    private String topMargin;

    @Column
    private String helpDisplay;

    @Column
    private String helpTitle;

    @Column
    private Integer helpContentType;

    @Column
    private String helpContent;

    @Column
    private String memo;
}