package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API 메뉴 관리 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAccessibleIpService extends AbstractBaseService<ApiAccessibleIpCommand, ApiAccessibleIpQuery, ApiAccessibleIpResult, Integer> {

    private final ApiAccessibleIpRepository apiAccessibleIpRepository;
    private final ApiMenuGroupRepository apiMenuGroupRepository;


}

