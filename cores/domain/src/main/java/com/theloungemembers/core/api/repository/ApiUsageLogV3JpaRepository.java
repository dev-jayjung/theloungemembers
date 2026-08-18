package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiUsageLogV3Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiUsageLogV3JpaRepository extends JpaRepository<ApiUsageLogV3Entity, Long>, JpaSpecificationExecutor<ApiUsageLogV3Entity> {
}
