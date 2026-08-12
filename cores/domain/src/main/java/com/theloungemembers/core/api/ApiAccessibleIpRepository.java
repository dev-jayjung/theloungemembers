package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiAccessibleIpEntity;
import com.theloungemembers.core.api.mapper.ApiAccessibleIpMapper;
import com.theloungemembers.core.api.repository.ApiAccessibleIpJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * API 접솝 허용 IP 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiAccessibleIpRepository
    extends AbstractBaseRepository<ApiAccessibleIpCommand, ApiAccessibleIpQuery, ApiAccessibleIpResult, Integer, ApiAccessibleIpEntity, ApiAccessibleIpMapper, ApiAccessibleIpJpaRepository> {

    public Integer selectUidByIpAddress(String ipAddress) {
        return mapper.selectUidByIpAddress(ipAddress);
    }

}
