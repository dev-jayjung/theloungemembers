package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiUsageLogV3Query;
import com.theloungemembers.core.api.ApiUsageLogV3Result;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiUsageLogV3Mapper extends BaseMapper<ApiUsageLogV3Query, ApiUsageLogV3Result, Long> {
}
