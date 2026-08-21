package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiSendCompanyDataQuery;
import com.theloungemembers.core.api.ApiSendCompanyDataResult;
import com.theloungemembers.core.api.ApiSendCompanyDataService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiSendCompanyDataResponse;
import com.theloungemembers.web.api.dto.ApiSendCompanyDataSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-send-company-data")
@RequiredArgsConstructor
public class ApiSendCompanyDataRestController {

    private final ApiSendCompanyDataService apiSendCompanyDataService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiSendCompanyDataResponse>>> getList(ApiSendCompanyDataSearchRequest request) {
        final ApiSendCompanyDataQuery query = modelMapperHelper.map(request, ApiSendCompanyDataQuery.class);
        final PageResponse<ApiSendCompanyDataResult> page = apiSendCompanyDataService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiSendCompanyDataResponse.class)));
    }

}
