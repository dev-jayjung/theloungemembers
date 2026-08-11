package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiUsageLogQuery;
import com.theloungemembers.core.api.ApiUsageLogResult;
import com.theloungemembers.core.api.ApiUsageLogService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiUsageLogResponse;
import com.theloungemembers.web.api.dto.ApiUsageLogSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * API 이용 로그 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/api-usage-logs")
@RequiredArgsConstructor
public class ApiUsageLogRestController {

    private final ApiUsageLogService apiUsageLogService;
    private final ModelMapperHelper modelMapperHelper;


    /**
     * API 이용 로그 목록 조회
     *
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiUsageLogResponse>>> getList(ApiUsageLogSearchRequest request) {
        final ApiUsageLogQuery query = modelMapperHelper.map(request, ApiUsageLogQuery.class);
        final PageResponse<ApiUsageLogResult> page = apiUsageLogService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiUsageLogResponse.class)));
    }


}
