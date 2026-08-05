package com.theloungemembers.app.lounge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.app.lounge.dto.LoungeRequest;
import com.theloungemembers.app.lounge.dto.LoungeResponse;
import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.lounge.LoungeQuery;
import com.theloungemembers.core.lounge.LoungeResult;
import com.theloungemembers.core.lounge.LoungeService;
import com.theloungemembers.core.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lounges")
@RequiredArgsConstructor
public class LoungeController {

    private final LoungeService loungeService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<LoungeResponse>>> getList(LoungeRequest request) {
        LoungeQuery query = modelMapperHelper.map(request, LoungeQuery.class);

        PageResponse<LoungeResult> page = loungeService.getPage(query);

        return ResponseUtil.success(page.map(modelMapperHelper.map(LoungeResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoungeResponse>> get(@PathVariable Integer id) {
        return ResponseUtil.success(modelMapperHelper.map(loungeService.get(id), LoungeResponse.class));
    }
}