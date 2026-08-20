package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiSendCompanyInfoCommand;
import com.theloungemembers.core.api.ApiSendCompanyInfoQuery;
import com.theloungemembers.core.api.ApiSendCompanyInfoResult;
import com.theloungemembers.core.api.ApiSendCompanyInfoService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiSendCompanyInfoCreateRequest;
import com.theloungemembers.web.api.dto.ApiSendCompanyInfoResponse;
import com.theloungemembers.web.api.dto.ApiSendCompanyInfoSearchRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-send-company-infos")
@RequiredArgsConstructor
public class ApiSendCompanyInfoRestController {

    /**
     * PHP 원본 등록/수정 화면(api_company_send_detail.html)에도 노출되지 않고 DB 기본값에 의존하던 컬럼.
     * Java 쪽 엔티티가 nullable=false라 저장 시 명시적으로 채워야 함.
     */
    private static final String DEFAULT_METHOD = "POST";
    private static final Integer DEFAULT_CANCEL_USE = 0;

    private final ApiSendCompanyInfoService apiSendCompanyInfoService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiSendCompanyInfoResponse>>> getList(ApiSendCompanyInfoSearchRequest request) {
        final ApiSendCompanyInfoQuery query = modelMapperHelper.map(request, ApiSendCompanyInfoQuery.class);
        final PageResponse<ApiSendCompanyInfoResult> page = apiSendCompanyInfoService.getPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiSendCompanyInfoResponse.class)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApiSendCompanyInfoResponse>> create(@RequestBody ApiSendCompanyInfoCreateRequest request) {
        final ApiSendCompanyInfoCommand command = modelMapperHelper.map(request, ApiSendCompanyInfoCommand.class);
        command.setMethod(DEFAULT_METHOD);
        command.setCancelUse(DEFAULT_CANCEL_USE);
        final ApiSendCompanyInfoResult result = apiSendCompanyInfoService.save(command);
        return ResponseUtil.success(modelMapperHelper.map(result, ApiSendCompanyInfoResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @RequestBody ApiSendCompanyInfoCreateRequest request) {
        final ApiSendCompanyInfoCommand command = modelMapperHelper.map(request, ApiSendCompanyInfoCommand.class);
        command.setUid(id);

        // PHP ajax.php도 이 두 필드는 UPDATE 문에 포함하지 않아 기존 값이 그대로 유지됨 -
        // ModelMapper는 null도 덮어쓰므로 기존 값을 그대로 다시 채워 넣어 덮어쓰기를 막는다.
        final ApiSendCompanyInfoResult current = apiSendCompanyInfoService.get(id);
        command.setMethod(current.getMethod());
        command.setCancelUse(current.getCancelUse());

        apiSendCompanyInfoService.update(id, command);
        return ResponseUtil.success();
    }

}
