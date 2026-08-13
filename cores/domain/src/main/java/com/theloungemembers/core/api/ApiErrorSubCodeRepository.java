package com.theloungemembers.core.api;

import com.theloungemembers.core.api.mapper.ApiErrorSubCodeMapper;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.PageUtil;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiErrorSubCodeRepository {

    private final ApiErrorSubCodeMapper mapper;

    public PageResponse<ApiErrorSubCodeResult> selectPage(ApiErrorSubCodeQuery query) {
        AssertUtil.notNull(query);
        return PageUtil.getPage(query, () -> mapper.selectList(query), () -> mapper.selectCount(query));
    }

}
