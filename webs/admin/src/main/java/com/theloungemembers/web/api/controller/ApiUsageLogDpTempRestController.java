package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiUsageLogDpTempQuery;
import com.theloungemembers.core.api.ApiUsageLogDpTempResult;
import com.theloungemembers.core.api.ApiUsageLogDpTempService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiUsageLogDpTempResponse;
import com.theloungemembers.web.api.dto.ApiUsageLogDpTempSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-usage-log-dp-temps")
@RequiredArgsConstructor
public class ApiUsageLogDpTempRestController {

    private final ApiUsageLogDpTempService apiUsageLogDpTempService;
    private final ModelMapperHelper modelMapperHelper;


    /**
     * API 메뉴 목록 조회
     *
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiUsageLogDpTempResponse>>> getList(ApiUsageLogDpTempSearchRequest request) {
        final ApiUsageLogDpTempQuery query = modelMapperHelper.map(request, ApiUsageLogDpTempQuery.class);
        final PageResponse<ApiUsageLogDpTempResult> page = apiUsageLogDpTempService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiUsageLogDpTempResponse.class)));
    }

}
