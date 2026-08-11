package com.theloungemembers.core.api;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.api.entity.ApiMenuGroupEntity;
import com.theloungemembers.core.api.mapper.ApiMenuGroupMapper;
import com.theloungemembers.core.api.repository.ApiMenuGroupJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiMenuGroupRepository extends AbstractBaseRepository<ApiMenuGroupCommand, ApiMenuGroupQuery, ApiMenuGroupResult, Long, ApiMenuGroupEntity, ApiMenuGroupMapper, ApiMenuGroupJpaRepository> {

}