package com.theloungemembers.core.api;

import com.theloungemembers.core.api.mapper.ApiUsageLogMapper;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * API 이용 로그 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiUsageLogRepository {

    private final ApiUsageLogMapper mapper;

    public PageResponse<ApiUsageLogResult> selectPage(ApiUsageLogQuery query) {
        AssertUtil.notNull(query);
        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }

    public List<ApiUsageLogResult> selectDpFailList(Long uid) {
        return mapper.selectDpFailList(uid);
    }

    public OffsetDateTime selectDpLatestRegDate(List<String> apiCodes) {
        return mapper.selectDpLatestRegDate(apiCodes);
    }
    
}
