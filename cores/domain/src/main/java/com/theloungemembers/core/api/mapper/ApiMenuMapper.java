package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuSearchResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMenuMapper extends BaseMapper<ApiMenuQuery, ApiMenuResult, Long> {
    boolean existsApiMenu(String code);

    /**
     * API 메뉴 갯수 조회
     *
     * @param query
     * @return
     */
    int selectSearchCount(ApiMenuQuery query);

    /**
     * API 메뉴 목록 조회
     *
     * @param query
     * @return
     */
    List<ApiMenuSearchResult> selectSearchList(ApiMenuQuery query);

    Long selectUidByCode(String code);

}