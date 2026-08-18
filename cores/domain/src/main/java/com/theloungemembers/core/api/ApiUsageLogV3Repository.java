package com.theloungemembers.core.api;

import com.theloungemembers.core.api.mapper.ApiUsageLogV3Mapper;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * API 이용 로그 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiUsageLogV3Repository {

    private final ApiUsageLogV3Mapper mapper;

    public PageResponse<ApiUsageLogV3Result> selectPage(ApiUsageLogV3Query query) {
        AssertUtil.notNull(query);
        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }


}
