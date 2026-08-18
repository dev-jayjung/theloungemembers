package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiUsageLogV3Service {

    private final ApiUsageLogV3Repository apiUsageLogV3Repository;

    @Transactional(readOnly = true)
    public PageResponse<ApiUsageLogV3Result> getPage(ApiUsageLogV3Query query) {
        AssertUtil.notNull(query);

        return apiUsageLogV3Repository.selectPage(query);
    }

}
