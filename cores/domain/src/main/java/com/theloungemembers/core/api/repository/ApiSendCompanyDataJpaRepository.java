package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiSendCompanyDataEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiSendCompanyDataJpaRepository extends JpaRepository<ApiSendCompanyDataEntity, Long>, JpaSpecificationExecutor<ApiSendCompanyDataEntity> {
}