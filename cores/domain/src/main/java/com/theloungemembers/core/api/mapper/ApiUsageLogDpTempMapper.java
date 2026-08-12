package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiUsageLogDpTempQuery;
import com.theloungemembers.core.api.ApiUsageLogDpTempResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiUsageLogDpTempMapper extends BaseMapper<ApiUsageLogDpTempQuery, ApiUsageLogDpTempResult, Long> {

}
