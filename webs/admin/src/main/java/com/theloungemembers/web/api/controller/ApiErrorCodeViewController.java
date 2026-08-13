package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-error-codes")
@RequiredArgsConstructor
public class ApiErrorCodeViewController {


    @GetMapping
    public String apiErrorCodeList(Model model) {
        return "api/error_code_list";
    }

}
