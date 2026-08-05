package com.theloungemembers.core.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface ApiMenuMapper extends BaseMapper<ApiMenuQuery, ApiMenuResult, Integer> {
    boolean existsApiMenu(String code);
}