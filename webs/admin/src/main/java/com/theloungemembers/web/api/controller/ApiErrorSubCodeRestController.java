package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiErrorSubCodeQuery;
import com.theloungemembers.core.api.ApiErrorSubCodeResult;
import com.theloungemembers.core.api.ApiErrorSubCodeService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiErrorSubCodeResponse;
import com.theloungemembers.web.api.dto.ApiErrorSubCodeSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-error-sub-codes")
@RequiredArgsConstructor
public class ApiErrorSubCodeRestController {

    private final ApiErrorSubCodeService apiErrorSubCodeService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiErrorSubCodeResponse>>> getList(ApiErrorSubCodeSearchRequest request) {
        final ApiErrorSubCodeQuery query = modelMapperHelper.map(request, ApiErrorSubCodeQuery.class);
        final PageResponse<ApiErrorSubCodeResult> page = apiErrorSubCodeService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiErrorSubCodeResponse.class)));
    }

}
