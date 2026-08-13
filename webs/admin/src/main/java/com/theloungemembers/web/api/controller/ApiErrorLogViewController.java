package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMemberQuery;
import com.theloungemembers.core.api.ApiMemberResult;
import com.theloungemembers.core.api.ApiMemberService;
import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiMemberResponse;
import com.theloungemembers.web.api.dto.ApiMenuResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-error-logs")
@RequiredArgsConstructor
public class ApiErrorLogViewController {

    private final ApiMemberService apiMemberService;
    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public String apiErrorLogList(
            @RequestParam(required = false) String searchTarget,
            @RequestParam(required = false) String searchValue,
            Model model) {

        final ApiMemberQuery memberQuery = new ApiMemberQuery();
        memberQuery.setOnService(ServiceStatus.IN_SERVICE);
        final List<ApiMemberResult> memberList = apiMemberService.getList(memberQuery);
        model.addAttribute("apiMemberList", modelMapperHelper.mapList(memberList, ApiMemberResponse.class));

        final ApiMenuQuery menuQuery = new ApiMenuQuery();
        menuQuery.setOnService(ServiceStatus.IN_SERVICE);
        final List<ApiMenuResult> menuList = apiMenuService.getList(menuQuery);
        model.addAttribute("apiMenuList", modelMapperHelper.mapList(menuList, ApiMenuResponse.class));

        model.addAttribute("searchTarget", searchTarget);
        model.addAttribute("searchValue", searchValue);

        return "api/error_log_list";
    }

}
