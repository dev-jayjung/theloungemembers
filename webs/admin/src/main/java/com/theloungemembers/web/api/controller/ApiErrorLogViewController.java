package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.helper.ModelMapperHelper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-error-logs")
@RequiredArgsConstructor
public class ApiErrorLogViewController {

    private final ModelMapperHelper modelMapperHelper;


    @GetMapping
    public String apiErrorLogList(Model model) {


        return "api/error_log_list";
    }

}
