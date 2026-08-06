package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuSearchResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMenuMapper extends BaseMapper<ApiMenuQuery, ApiMenuResult, Integer> {

    boolean existsApiMenu(String code);

    int selectSearchCount(ApiMenuQuery query);

    List<ApiMenuSearchResult> selectSearchList(ApiMenuQuery query);
    
}