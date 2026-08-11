package com.theloungemembers.web.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.core.admin.AdminMenuResult;
import com.theloungemembers.core.admin.AdminMenuService;
import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.util.ResponseUtil;
import com.theloungemembers.web.admin.dto.AdminMenuResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin-menus")
@RequiredArgsConstructor
public class AdminMenuRestController {

    private final AdminMenuService adminMenuService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping("/by-url")
    public ResponseEntity<ApiResponse<AdminMenuResponse>> getAdminMenuByUrl(@RequestParam String url) {
        AdminMenuResult result = adminMenuService.getMenuTitle(url);

        return ResponseUtil.success(modelMapperHelper.map(result, AdminMenuResponse.class));
    }

}