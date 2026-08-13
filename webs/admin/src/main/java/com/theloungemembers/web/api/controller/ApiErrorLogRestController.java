package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiErrorLogQuery;
import com.theloungemembers.core.api.ApiErrorLogResult;
import com.theloungemembers.core.api.ApiErrorLogService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiErrorLogResponse;
import com.theloungemembers.web.api.dto.ApiErrorLogSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-error-logs")
@RequiredArgsConstructor
public class ApiErrorLogRestController {

    private final ApiErrorLogService apiErrorLogService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiErrorLogResponse>>> getList(ApiErrorLogSearchRequest request) {
        final ApiErrorLogQuery query = modelMapperHelper.map(request, ApiErrorLogQuery.class);
        final PageResponse<ApiErrorLogResult> page = apiErrorLogService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiErrorLogResponse.class)));
    }

}
