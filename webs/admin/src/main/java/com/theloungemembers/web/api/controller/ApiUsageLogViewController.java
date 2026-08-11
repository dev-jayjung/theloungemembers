package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.helper.ModelMapperHelper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-usage-logs")
@RequiredArgsConstructor
public class ApiUsageLogViewController {

    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public String apiUsageLogList(Model model) {


        return "api/usage_log_list";
    }


}