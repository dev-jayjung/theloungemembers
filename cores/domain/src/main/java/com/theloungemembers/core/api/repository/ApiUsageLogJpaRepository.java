package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiUsageLogEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiUsageLogJpaRepository extends JpaRepository<ApiUsageLogEntity, Long>, JpaSpecificationExecutor<ApiUsageLogEntity> {
}
