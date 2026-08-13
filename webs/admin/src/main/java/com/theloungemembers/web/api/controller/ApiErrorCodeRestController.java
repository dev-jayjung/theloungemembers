package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiErrorCodeQuery;
import com.theloungemembers.core.api.ApiErrorCodeResult;
import com.theloungemembers.core.api.ApiErrorCodeService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiErrorCodeResponse;
import com.theloungemembers.web.api.dto.ApiErrorCodeSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-error-codes")
@RequiredArgsConstructor
public class ApiErrorCodeRestController {

    private final ApiErrorCodeService apiErrorCodeService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiErrorCodeResponse>>> getList(ApiErrorCodeSearchRequest request) {
        final ApiErrorCodeQuery query = modelMapperHelper.map(request, ApiErrorCodeQuery.class);
        final PageResponse<ApiErrorCodeResult> page = apiErrorCodeService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiErrorCodeResponse.class)));
    }

}
