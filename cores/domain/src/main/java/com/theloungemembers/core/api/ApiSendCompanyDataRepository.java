package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiSendCompanyDataEntity;
import com.theloungemembers.core.api.mapper.ApiSendCompanyDataMapper;
import com.theloungemembers.core.api.repository.ApiSendCompanyDataJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * API 메뉴 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiSendCompanyDataRepository
    extends AbstractBaseRepository<Void, ApiSendCompanyDataQuery, ApiSendCompanyDataResult, Long, ApiSendCompanyDataEntity, ApiSendCompanyDataMapper, ApiSendCompanyDataJpaRepository> {

}
