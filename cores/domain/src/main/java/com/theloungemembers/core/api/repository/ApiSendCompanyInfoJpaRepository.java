package com.theloungemembers.core.api.repository;

import com.theloungemembers.core.api.entity.ApiSendCompanyInfoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiSendCompanyInfoJpaRepository extends JpaRepository<ApiSendCompanyInfoEntity, Long>, JpaSpecificationExecutor<ApiSendCompanyInfoEntity> {
}
