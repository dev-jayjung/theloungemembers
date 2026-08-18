package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiUsageLogV3WebhookQuery;
import com.theloungemembers.core.api.ApiUsageLogV3WebhookResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiUsageLogV3WebhookMapper extends BaseMapper<ApiUsageLogV3WebhookQuery, ApiUsageLogV3WebhookResult, Long> {
}
