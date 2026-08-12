package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiUsageLogEntity;
import com.theloungemembers.core.api.mapper.ApiUsageLogMapper;
import com.theloungemembers.core.api.repository.ApiUsageLogJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * API 이용 로그 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiUsageLogRepository extends AbstractBaseRepository<ApiUsageLogCommand, ApiUsageLogQuery, ApiUsageLogResult, Long, ApiUsageLogEntity, ApiUsageLogMapper, ApiUsageLogJpaRepository> {

    public List<ApiUsageLogResult> selectDragonpassFailList(Long uid) {
        return mapper.selectDragonpassFailList(uid);
    }
}
