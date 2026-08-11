package com.theloungemembers.core.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface ApiMenuGroupMapper extends BaseMapper<ApiMenuGroupQuery, ApiMenuGroupResult, Long> {
}