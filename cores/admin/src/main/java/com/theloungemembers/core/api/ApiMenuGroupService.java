package com.theloungemembers.core.api;

import org.springframework.stereotype.Service;

import com.theloungemembers.core.common.crud.AbstractBaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiMenuGroupService
        extends AbstractBaseService<ApiMenuGroupCommand, ApiMenuGroupQuery, ApiMenuGroupResult, Long> {

}