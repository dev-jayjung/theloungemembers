package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuSearchResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiMenuSearchReqeust;
import com.theloungemembers.web.api.dto.ApiMenuSearchResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-menus")
@RequiredArgsConstructor
public class ApiMenuRestController {

    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiMenuSearchResponse>>> getApiMenuList(ApiMenuSearchReqeust request) {

        ApiMenuQuery query = modelMapperHelper.map(request, ApiMenuQuery.class);

        PageResponse<ApiMenuSearchResult> page = apiMenuService.getSearchPage(query);
        
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiMenuSearchResponse.class)));
    }


}