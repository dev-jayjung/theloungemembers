package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-usage-logs-v3")
public class ApiUsageLogV3ViewController {

    @GetMapping
    public String apiUsageLogV3List() {
        return "api/usage_log_v3_list";
    }

}
