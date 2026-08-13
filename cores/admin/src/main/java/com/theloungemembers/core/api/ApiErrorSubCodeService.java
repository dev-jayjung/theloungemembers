package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiErrorSubCodeService {

    private final ApiErrorSubCodeRepository apiErrorSubCodeRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiErrorSubCodeResult> getPage(ApiErrorSubCodeQuery query) {
        AssertUtil.notNull(query);

        return apiErrorSubCodeRepository.selectPage(query);
    }
}
