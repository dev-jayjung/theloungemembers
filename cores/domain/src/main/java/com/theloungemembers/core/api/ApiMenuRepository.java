package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiMenuEntity;
import com.theloungemembers.core.api.mapper.ApiMenuMapper;
import com.theloungemembers.core.api.repository.ApiMenuJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiMenuRepository extends AbstractBaseRepository<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Integer, ApiMenuEntity, ApiMenuMapper, ApiMenuJpaRepository> {

    public int selectSearchCount(ApiMenuQuery query) {
        return mapper.selectSearchCount(query);
    }

    public List<ApiMenuSearchResult> selectSearchList(ApiMenuQuery query) {
        return mapper.selectSearchList(query);
    }

}