package com.theloungemembers.core.api;

import com.theloungemembers.core.common.dto.PageResponse;
import com.theloungemembers.core.util.AssertUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiSendCompanyDataService {

    private final ApiSendCompanyDataRepository apiSendCompanyDataRepository;

    @Transactional(readOnly = true)
    public PageResponse<ApiSendCompanyDataResult> getPage(ApiSendCompanyDataQuery query) {
        AssertUtil.notNull(query);

        return apiSendCompanyDataRepository.selectPage(query);
    }

}
