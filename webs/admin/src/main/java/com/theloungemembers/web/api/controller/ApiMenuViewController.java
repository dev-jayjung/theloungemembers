package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.api.ApiMenuGroupService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.web.api.dto.ApiMenuGroupResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-menus")
@RequiredArgsConstructor
public class ApiMenuViewController {

    private final ApiMenuGroupService apiMenuGroupService;
    private final ModelMapperHelper modelMapperHelper;

    
    @GetMapping
    public String apiMemberList(Model model) {

        List<ApiMenuGroupResult> apiMenuGroupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(apiMenuGroupList, ApiMenuGroupResponse.class));

        return "api/menu_list";
    }


}