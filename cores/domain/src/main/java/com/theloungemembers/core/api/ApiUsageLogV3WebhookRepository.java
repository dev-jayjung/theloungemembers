package com.theloungemembers.core.api;

import com.theloungemembers.core.api.mapper.ApiUsageLogV3WebhookMapper;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiUsageLogV3WebhookRepository {

    private final ApiUsageLogV3WebhookMapper mapper;

    public PageResponse<ApiUsageLogV3WebhookResult> selectPage(ApiUsageLogV3WebhookQuery query) {
        AssertUtil.notNull(query);
        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }

}
