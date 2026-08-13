package com.theloungemembers.core.api;

import com.theloungemembers.core.api.mapper.ApiErrorLogMapper;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiErrorLogRepository {

    private final ApiErrorLogMapper mapper;

    public PageResponse<ApiErrorLogResult> selectPage(ApiErrorLogQuery query) {
        AssertUtil.notNull(query);
        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }

}
