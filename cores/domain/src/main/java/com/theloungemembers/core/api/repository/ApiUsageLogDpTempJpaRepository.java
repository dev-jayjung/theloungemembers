package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiUsageLogDpTempEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiUsageLogDpTempJpaRepository extends JpaRepository<ApiUsageLogDpTempEntity, Long>, JpaSpecificationExecutor<ApiUsageLogDpTempEntity> {
}
