package com.theloungemembers.core.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.api.entity.ApiMenuGroupEntity;

public interface ApiMenuGroupJpaRepository extends JpaRepository<ApiMenuGroupEntity, Integer>, JpaSpecificationExecutor<ApiMenuGroupEntity> {}