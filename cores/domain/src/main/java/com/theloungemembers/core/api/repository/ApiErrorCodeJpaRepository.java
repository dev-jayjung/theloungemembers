package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiErrorCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiErrorCodeJpaRepository extends JpaRepository<ApiErrorCodeEntity, Long>, JpaSpecificationExecutor<ApiErrorCodeEntity> {
}