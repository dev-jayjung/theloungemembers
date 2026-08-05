package com.theloungemembers.core.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.api.ApiMemberQuery;
import com.theloungemembers.core.api.ApiMemberResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface ApiMemberMapper extends BaseMapper<ApiMemberQuery, ApiMemberResult, Integer> {
}