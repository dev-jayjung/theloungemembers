package com.theloungemembers.web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-usage-logs-v3-webhook")
public class ApiUsageLogV3WebhookViewController {

    @GetMapping
    public String apiUsageLogV3WebhookList() {
        return "api/usage_log_v3_webhook_list";
    }

}