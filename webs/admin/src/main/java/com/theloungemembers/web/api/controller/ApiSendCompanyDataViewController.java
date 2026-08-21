package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-send-company-data")
public class ApiSendCompanyDataViewController {

    @GetMapping
    public String apiSendCompanyDataList() {
        return "api/send_company_data_list";
    }

}
