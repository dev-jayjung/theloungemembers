package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiUsageLogService {

    private final ApiUsageLogRepository apiUsageLogRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiUsageLogResult> getPage(ApiUsageLogQuery query) {
        AssertUtil.notNull(query);

        return apiUsageLogRepository.selectPage(query);
    }

    @Transactional(readOnly = true)
    public OffsetDateTime getDpLatestRegDate(List<String> apiCodes) {
        AssertUtil.notNull(apiCodes);

        return apiUsageLogRepository.selectDpLatestRegDate(apiCodes);
    }
}
