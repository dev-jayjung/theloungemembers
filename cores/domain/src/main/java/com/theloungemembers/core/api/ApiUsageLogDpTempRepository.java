package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiUsageLogDpTempEntity;
import com.theloungemembers.core.api.mapper.ApiUsageLogDpTempMapper;
import com.theloungemembers.core.api.repository.ApiUsageLogDpTempJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiUsageLogDpTempRepository
    extends AbstractBaseRepository<ApiUsageLogDpTempCommand, ApiUsageLogDpTempQuery, ApiUsageLogDpTempResult, Long, ApiUsageLogDpTempEntity, ApiUsageLogDpTempMapper, ApiUsageLogDpTempJpaRepository> {
}
