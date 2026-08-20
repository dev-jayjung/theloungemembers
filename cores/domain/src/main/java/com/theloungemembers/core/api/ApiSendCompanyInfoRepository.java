package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiSendCompanyInfoEntity;
import com.theloungemembers.core.api.entity.ApiSendCompanyInfoJpaRepository;
import com.theloungemembers.core.api.mapper.ApiSendCompanyInfoMapper;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * API 메뉴 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiSendCompanyInfoRepository extends
    AbstractBaseRepository<ApiSendCompanyInfoCommand, ApiSendCompanyInfoQuery, ApiSendCompanyInfoResult, Long, ApiSendCompanyInfoEntity, ApiSendCompanyInfoMapper, ApiSendCompanyInfoJpaRepository> {

}
