package com.theloungemembers.web.api.controller;

import com.theloungemembers.core.api.ApiUsageLogService;
import com.theloungemembers.core.worker.WorkerSmsReceiverResult;
import com.theloungemembers.core.worker.WorkerSmsReceiverService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api-monitor")
@RequiredArgsConstructor
public class ApiMonitorViewController {

    private static final List<String> DRAGONPASS_API_CODES = List.of("coupon_use", "coupon_info", "coupon_cancel");
    private static final String CONCERN_PAGE_API_MONITOR = "api_monitor";
    private static final String CONCERN_PAGE_API_FAIL_MONITOR = "api_fail_monitor";

    private final ApiUsageLogService apiUsageLogService;
    private final WorkerSmsReceiverService workerSmsReceiverService;

    @GetMapping
    public String apiMonitor(Model model) {
        final OffsetDateTime lastUsageRegDate = apiUsageLogService.getDpLatestRegDate(DRAGONPASS_API_CODES);
        model.addAttribute("lastUsageRegDate", lastUsageRegDate);

        final List<WorkerSmsReceiverResult> apiMonitorReceiverList = workerSmsReceiverService.getListByConcernPageCode(CONCERN_PAGE_API_MONITOR);
        model.addAttribute("apiReceiverList", apiMonitorReceiverList);

        final List<WorkerSmsReceiverResult> apiFailMonitorReceiverList = workerSmsReceiverService.getListByConcernPageCode(CONCERN_PAGE_API_FAIL_MONITOR);
        model.addAttribute("apiFailReceiverList", apiFailMonitorReceiverList);

        return "api/monitor";
    }
}