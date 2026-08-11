package com.theloungemembers.core.lounge.entity;

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
@Table(name = "lounge")
public class LoungeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(name = "code")
    private String code;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "company_affiliated_code")
    private String companyAffiliatedCode;

    @Column(name = "city_uid")
    private Long cityUid;

    @Column(name = "airport_uid")
    private Long airportUid;

    @Column(name = "name")
    private String name;
}