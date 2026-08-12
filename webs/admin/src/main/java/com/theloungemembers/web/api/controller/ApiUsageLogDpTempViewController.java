package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-usage-log-dp-temps")
@RequiredArgsConstructor
public class ApiUsageLogDpTempViewController {

    @GetMapping
    public String apiUsageLogDpTempList(Model model) {

        return "api/usage_log_dp_temp_list";
    }

}
