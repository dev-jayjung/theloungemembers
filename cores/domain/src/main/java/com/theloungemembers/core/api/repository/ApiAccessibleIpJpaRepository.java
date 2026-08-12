package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiAccessibleIpEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiAccessibleIpJpaRepository extends JpaRepository<ApiAccessibleIpEntity, Integer>, JpaSpecificationExecutor<ApiAccessibleIpEntity> {
}
