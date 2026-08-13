package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiErrorSubCodeQuery;
import com.theloungemembers.core.api.ApiErrorSubCodeResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiErrorSubCodeMapper extends BaseMapper<ApiErrorSubCodeQuery, ApiErrorSubCodeResult, Long> {
}
