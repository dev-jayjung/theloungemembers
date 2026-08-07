package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiMenuGroupMapper extends BaseMapper<ApiMenuGroupQuery, ApiMenuGroupResult, Integer> {

    boolean existsCode(String code);

}