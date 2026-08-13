package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiErrorCodeQuery;
import com.theloungemembers.core.api.ApiErrorCodeResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiErrorCodeMapper extends BaseMapper<ApiErrorCodeQuery, ApiErrorCodeResult, Long> {
}
