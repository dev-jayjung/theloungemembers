package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiSendCompanyInfoService extends AbstractBaseService<ApiSendCompanyInfoCommand, ApiSendCompanyInfoQuery, ApiSendCompanyInfoResult, Long> {

}
