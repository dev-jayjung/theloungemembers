package com.theloungemembers.core.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.api.entity.ApiMenuEntity;

public interface ApiMenuJpaRepository extends JpaRepository<ApiMenuEntity, Integer>, JpaSpecificationExecutor<ApiMenuEntity> {}