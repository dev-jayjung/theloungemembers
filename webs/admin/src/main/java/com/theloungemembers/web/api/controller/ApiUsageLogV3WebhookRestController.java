package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiUsageLogV3WebhookQuery;
import com.theloungemembers.core.api.ApiUsageLogV3WebhookResult;
import com.theloungemembers.core.api.ApiUsageLogV3WebhookService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiUsageLogV3WebhookResponse;
import com.theloungemembers.web.api.dto.ApiUsageLogV3WebhookSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * V3 API 이용 로그(Webhook) 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/api-usage-logs-v3-webhook")
@RequiredArgsConstructor
public class ApiUsageLogV3WebhookRestController {

    private final ApiUsageLogV3WebhookService apiUsageLogV3WebhookService;
    private final ModelMapperHelper modelMapperHelper;


    /**
     * V3 API 이용 로그(Webhook) 목록 조회
     *
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiUsageLogV3WebhookResponse>>> getList(ApiUsageLogV3WebhookSearchRequest request) {
        final ApiUsageLogV3WebhookQuery query = modelMapperHelper.map(request, ApiUsageLogV3WebhookQuery.class);
        final PageResponse<ApiUsageLogV3WebhookResult> page = apiUsageLogV3WebhookService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiUsageLogV3WebhookResponse.class)));
    }

}
