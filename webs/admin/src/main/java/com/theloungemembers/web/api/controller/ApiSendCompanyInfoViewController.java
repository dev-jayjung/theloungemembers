package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiMemberQuery;
import com.theloungemembers.core.api.ApiMemberResult;
import com.theloungemembers.core.api.ApiMemberService;
import com.theloungemembers.core.api.ApiSendCompanyInfoResult;
import com.theloungemembers.core.api.ApiSendCompanyInfoService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiMemberResponse;
import com.theloungemembers.web.api.dto.ApiSendCompanyInfoResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-send-company-infos")
@RequiredArgsConstructor
public class ApiSendCompanyInfoViewController {

    private final ApiSendCompanyInfoService apiSendCompanyInfoService;
    private final ApiMemberService apiMemberService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public String apiSendCompanyInfoList(Model model) {
        return "api/send_company_info_list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        final ApiSendCompanyInfoResponse sendCompanyInfo = new ApiSendCompanyInfoResponse();
        sendCompanyInfo.setOnService(ServiceStatus.STOPPED);
        model.addAttribute("sendCompanyInfo", sendCompanyInfo);
        addApiMemberList(model);

        return "api/send_company_info_form";
    }

    @GetMapping("/detail")
    public String detailForm(@RequestParam Long id, Model model) {
        final ApiSendCompanyInfoResult result = apiSendCompanyInfoService.get(id);
        model.addAttribute("sendCompanyInfo", modelMapperHelper.map(result, ApiSendCompanyInfoResponse.class));
        addApiMemberList(model);

        return "api/send_company_info_form";
    }

    private void addApiMemberList(Model model) {
        final ApiMemberQuery memberQuery = new ApiMemberQuery();
        memberQuery.setOnService(ServiceStatus.IN_SERVICE);
        final List<ApiMemberResult> memberList = apiMemberService.getList(memberQuery);
        model.addAttribute("apiMemberList", modelMapperHelper.mapList(memberList, ApiMemberResponse.class));
    }

}