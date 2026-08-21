package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.api.ApiMenuGroupService;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiMenuGroupResponse;
import com.theloungemembers.web.api.dto.ApiMenuResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-menus")
@RequiredArgsConstructor
public class ApiMenuViewController {

    private final ApiMenuGroupService apiMenuGroupService;
    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping
    public String apiMenuList(Model model) {

        final List<ApiMenuGroupResult> groupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(groupList, ApiMenuGroupResponse.class));

        return "api/menu_list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        final ApiMenuResponse menu = new ApiMenuResponse();
        menu.setOnService(ServiceStatus.IN_SERVICE);
        model.addAttribute("menu", menu);

        final List<ApiMenuGroupResult> groupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(groupList, ApiMenuGroupResponse.class));

        return "api/menu_form";
    }

    @GetMapping("/detail")
    public String detailForm(@RequestParam Long id, Model model) {

        final ApiMenuResult menu = apiMenuService.get(id);
        model.addAttribute("menu", modelMapperHelper.map(menu, ApiMenuResponse.class));

        final List<ApiMenuGroupResult> groupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(groupList, ApiMenuGroupResponse.class));

        return "api/menu_form";
    }

}
