package com.theloungemembers.core.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.api.entity.ApiMenuPermissionEntity;

public interface ApiMenuPermissionJpaRepository extends JpaRepository<ApiMenuPermissionEntity, Long>, JpaSpecificationExecutor<ApiMenuPermissionEntity> {}