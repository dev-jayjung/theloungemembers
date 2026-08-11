package com.theloungemembers.core.api;

import com.theloungemembers.core.common.crud.AbstractBaseService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUsageLogService extends AbstractBaseService<ApiUsageLogCommand, ApiUsageLogQuery, ApiUsageLogResult, Integer> {

}