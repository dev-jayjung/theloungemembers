package com.theloungemembers.core.lounge;

import org.springframework.stereotype.Service;

import com.theloungemembers.core.common.crud.AbstractBaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoungeService extends AbstractBaseService<LoungeCommand, LoungeQuery, LoungeResult, Integer> {
}