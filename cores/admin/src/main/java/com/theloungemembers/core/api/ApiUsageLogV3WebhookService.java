package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiUsageLogV3WebhookService {

    private final ApiUsageLogV3WebhookRepository apiUsageLogV3WebhookRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiUsageLogV3WebhookResult> getPage(ApiUsageLogV3WebhookQuery query) {
        AssertUtil.notNull(query);
        return apiUsageLogV3WebhookRepository.selectPage(query);
    }

}
