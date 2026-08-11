package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMenuCommand;
import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuSearchResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.api.dto.ApiMenuCreateRequest;
import com.theloungemembers.web.api.dto.ApiMenuResponse;
import com.theloungemembers.web.api.dto.ApiMenuSearchRequest;
import com.theloungemembers.web.api.dto.ApiMenuSearchResponse;

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
 * API 메뉴 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/api-menus")
@RequiredArgsConstructor
public class ApiMenuRestController {

    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;


    /**
     * API 메뉴 목록 조회
     *
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApiMenuSearchResponse>>> getList(ApiMenuSearchRequest request) {
        final ApiMenuQuery query = modelMapperHelper.map(request, ApiMenuQuery.class);
        final PageResponse<ApiMenuSearchResult> page = apiMenuService.getSearchPage(query);
        return ResponseUtil.success(page.map(modelMapperHelper.map(ApiMenuSearchResponse.class)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApiMenuResponse>> create(@RequestBody ApiMenuCreateRequest request) {
        final ApiMenuCommand command = modelMapperHelper.map(request, ApiMenuCommand.class);
        apiMenuService.validate(command);
        final ApiMenuResult menu = apiMenuService.save(command);
        return ResponseUtil.success(modelMapperHelper.map(menu, ApiMenuResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @RequestBody ApiMenuCreateRequest request) {
        final ApiMenuCommand command = modelMapperHelper.map(request, ApiMenuCommand.class);
        command.setUid(id);
        apiMenuService.validate(command);
        apiMenuService.update(id, command);
        return ResponseUtil.success();
    }


}