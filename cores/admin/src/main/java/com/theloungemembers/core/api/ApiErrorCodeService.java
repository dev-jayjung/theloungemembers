package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiErrorCodeService {

    private final ApiErrorCodeRepository apiErrorCodeRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiErrorCodeResult> getPage(ApiErrorCodeQuery query) {
        AssertUtil.notNull(query);

        return apiErrorCodeRepository.selectPage(query);
    }
}
