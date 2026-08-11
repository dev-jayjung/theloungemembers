package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiMenuEntity;
import com.theloungemembers.core.api.mapper.ApiMenuMapper;
import com.theloungemembers.core.api.repository.ApiMenuJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * API 메뉴 저장소
 */
@Repository
@RequiredArgsConstructor
public class ApiMenuRepository extends AbstractBaseRepository<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Long, ApiMenuEntity, ApiMenuMapper, ApiMenuJpaRepository> {

    /**
     * 코드로 API 메뉴 키 조회
     *
     * @param code
     * @return
     */
    public Integer selectUidByCode(String code) {
        return mapper.selectUidByCode(code);
    }

    /**
     * API 메뉴 갯수 조회
     *
     * @param query
     * @return
     */
    public int selectSearchCount(ApiMenuQuery query) {
        return mapper.selectSearchCount(query);
    }

    /**
     * API 메뉴 목록 조회
     *
     * @param query
     * @return
     */
    public List<ApiMenuSearchResult> selectSearchList(ApiMenuQuery query) {
        return mapper.selectSearchList(query);
    }

}