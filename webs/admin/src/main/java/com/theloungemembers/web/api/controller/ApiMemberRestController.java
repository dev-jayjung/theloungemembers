package com.theloungemembers.web.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.core.api.ApiMemberCommand;
import com.theloungemembers.core.api.ApiMemberQuery;
import com.theloungemembers.core.api.ApiMemberResult;
import com.theloungemembers.core.api.ApiMemberService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiMemberCreateReqeust;
import com.theloungemembers.web.api.dto.ApiMemberResponse;
import com.theloungemembers.web.api.dto.ApiMemberSearchReqeust;
import com.theloungemembers.web.api.dto.ApiMemberUpdateReqeust;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/api-members")
@RequiredArgsConstructor
public class ApiMemberRestController {

    private final ApiMemberService apiMemberService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiMemberResponse>>> getApiMemberList(ApiMemberSearchReqeust request) {
        ApiMemberQuery query = modelMapperHelper.map(request, ApiMemberQuery.class);

        PageResponse<ApiMemberResult> page = apiMemberService.getPage(query);

        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiMemberResponse.class)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApiMemberResult>> createApiMember(@RequestBody ApiMemberCreateReqeust request) {
        ApiMemberCommand command = modelMapperHelper.map(request, ApiMemberCommand.class);

        ApiMemberResult res = apiMemberService.save(command);

        return ResponseUtil.success(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateApiMember(@RequestBody ApiMemberUpdateReqeust request) {
        ApiMemberCommand command = modelMapperHelper.map(request, ApiMemberCommand.class);

        apiMemberService.update(command);

        return ResponseUtil.success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteApiMember(@PathVariable Integer id) {
        apiMemberService.delete(id);

        return ResponseUtil.success();
    }
}