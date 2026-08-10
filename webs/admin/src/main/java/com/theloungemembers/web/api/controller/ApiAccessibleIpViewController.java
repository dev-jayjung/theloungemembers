package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiAccessibleIpResult;
import com.theloungemembers.core.api.ApiAccessibleIpService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiAccessibleIpResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-accessible-ips")
@RequiredArgsConstructor
public class ApiAccessibleIpViewController {

    private final ApiAccessibleIpService apiAccessibleIpService;
    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public String apiAccessibleIpList(Model model) {
        return "api/accessible_ip_list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        final ApiAccessibleIpResponse accessibleIp = new ApiAccessibleIpResponse();
        accessibleIp.setOnService(ServiceStatus.STOPPED);
        model.addAttribute("accessibleIp", accessibleIp);

        return "api/accessible_ip_form";
    }

    @GetMapping("/detail")
    public String detailForm(@RequestParam Integer id, Model model) {

        final ApiAccessibleIpResult menu = apiAccessibleIpService.get(id);
        model.addAttribute("accessibleIp", modelMapperHelper.map(menu, ApiAccessibleIpResponse.class));

        return "api/accessible_ip_form";
    }

}