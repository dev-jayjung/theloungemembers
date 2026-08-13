package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiErrorLogQuery;
import com.theloungemembers.core.api.ApiErrorLogResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiErrorLogMapper extends BaseMapper<ApiErrorLogQuery, ApiErrorLogResult, Long> {
}
