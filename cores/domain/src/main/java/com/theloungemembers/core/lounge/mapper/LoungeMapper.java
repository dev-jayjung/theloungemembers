package com.theloungemembers.core.lounge.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.common.crud.BaseMapper;
import com.theloungemembers.core.lounge.LoungeQuery;
import com.theloungemembers.core.lounge.LoungeResult;

@Mapper
public interface LoungeMapper extends BaseMapper<LoungeQuery, LoungeResult, Integer> {
}