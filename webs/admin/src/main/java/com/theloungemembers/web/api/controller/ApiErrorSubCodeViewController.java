package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-error-sub-codes")
@RequiredArgsConstructor
public class ApiErrorSubCodeViewController {


    @GetMapping
    public String apiErrorSubCodeList(Model model) {
        return "api/error_sub_code_list";
    }

}
