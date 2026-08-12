package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiAccessibleIpCommand;
import com.theloungemembers.core.api.ApiAccessibleIpQuery;
import com.theloungemembers.core.api.ApiAccessibleIpResult;
import com.theloungemembers.core.api.ApiAccessibleIpService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiAccessibleIpCreateRequest;
import com.theloungemembers.web.api.dto.ApiAccessibleIpResponse;
import com.theloungemembers.web.api.dto.ApiAccessibleIpSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * API 접속 허용 IP 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/api-accessible-ips")
@RequiredArgsConstructor
public class ApiAccessibleIpRestController {

    private final ApiAccessibleIpService apiAccessibleIpService;
    private final ModelMapperHelper modelMapperHelper;


    /**
     * API 접속 허용 IP 목록 조회
     *
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiAccessibleIpResponse>>> getList(ApiAccessibleIpSearchRequest request) {
        final ApiAccessibleIpQuery query = modelMapperHelper.map(request, ApiAccessibleIpQuery.class);
        final PageResponse<ApiAccessibleIpResult> page = apiAccessibleIpService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiAccessibleIpResponse.class)));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ApiAccessibleIpResponse>> create(@RequestBody ApiAccessibleIpCreateRequest request) {
        final ApiAccessibleIpCommand command = modelMapperHelper.map(request, ApiAccessibleIpCommand.class);
        apiAccessibleIpService.validate(command);
        final ApiAccessibleIpResult menu = apiAccessibleIpService.save(command);
        return ResponseUtil.success(modelMapperHelper.map(menu, ApiAccessibleIpResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @RequestBody ApiAccessibleIpCreateRequest request) {
        final ApiAccessibleIpCommand command = modelMapperHelper.map(request, ApiAccessibleIpCommand.class);
        command.setUid(id);
        apiAccessibleIpService.validate(command);
        apiAccessibleIpService.update(id, command);
        return ResponseUtil.success();
    }

}
