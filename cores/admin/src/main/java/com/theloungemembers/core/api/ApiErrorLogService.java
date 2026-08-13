package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiErrorLogService {

    private final ApiErrorLogRepository apiErrorLogRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiErrorLogResult> getPage(ApiErrorLogQuery query) {
        AssertUtil.notNull(query);
        return apiErrorLogRepository.selectPage(query);
    }

}
