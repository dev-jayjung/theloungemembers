package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.common.util.PageUtil;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMenuService extends AbstractBaseService<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Integer> {

    private final ApiMenuRepository apiMenuRepository;

    public PageResponse<ApiMenuSearchResult> getSearchPage(ApiMenuQuery query) {

        int count = apiMenuRepository.selectSearchCount(query);
        List<ApiMenuSearchResult> searchList = apiMenuRepository.selectSearchList(query);

        return PageUtil.getPage(query, () -> searchList, () -> count);
    }


}