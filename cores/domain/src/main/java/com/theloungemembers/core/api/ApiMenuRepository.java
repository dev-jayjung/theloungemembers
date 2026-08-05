package com.theloungemembers.core.api;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.api.entity.ApiMenuEntity;
import com.theloungemembers.core.api.mapper.ApiMenuMapper;
import com.theloungemembers.core.api.repository.ApiMenuJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiMenuRepository extends AbstractBaseRepository<ApiMenuCommand, ApiMenuQuery, ApiMenuResult, Integer, ApiMenuEntity, ApiMenuMapper, ApiMenuJpaRepository> {

}