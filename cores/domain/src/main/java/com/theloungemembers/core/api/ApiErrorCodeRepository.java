package com.theloungemembers.core.api;

import com.theloungemembers.core.api.entity.ApiErrorCodeEntity;
import com.theloungemembers.core.api.mapper.ApiErrorCodeMapper;
import com.theloungemembers.core.api.repository.ApiErrorCodeJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiErrorCodeRepository extends AbstractBaseRepository<Void, ApiErrorCodeQuery, ApiErrorCodeResult, Long, ApiErrorCodeEntity, ApiErrorCodeMapper, ApiErrorCodeJpaRepository> {
}
