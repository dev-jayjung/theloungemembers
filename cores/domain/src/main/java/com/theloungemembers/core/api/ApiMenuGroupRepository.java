package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiMenuGroupEntity;
import com.theloungemembers.core.api.mapper.ApiMenuGroupMapper;
import com.theloungemembers.core.api.repository.ApiMenuGroupJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.common.util.PageUtil;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiMenuGroupRepository
    extends AbstractBaseRepository<ApiMenuGroupCommand, ApiMenuGroupQuery, ApiMenuGroupResult, Integer, ApiMenuGroupEntity, ApiMenuGroupMapper, ApiMenuGroupJpaRepository> {

    public boolean existsCode(String code) {
        return mapper.existsCode(code);
    }

    public List<ApiMenuGroupResult> selectApiMenuGroupList(ApiMenuGroupQuery query) {
        AssertUtil.notNull(query);

        return mapper.selectList(query);
    }

    public PageResponse<ApiMenuGroupResult> selectApiMenuGroupPage(ApiMenuGroupQuery query) {
        AssertUtil.notNull(query);

        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }


}