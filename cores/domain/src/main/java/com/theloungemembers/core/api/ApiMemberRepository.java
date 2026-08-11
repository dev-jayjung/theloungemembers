package com.theloungemembers.core.api;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.api.entity.ApiMemberEntity;
import com.theloungemembers.core.api.mapper.ApiMemberMapper;
import com.theloungemembers.core.api.repository.ApiMemberJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class ApiMemberRepository extends AbstractBaseRepository<ApiMemberCommand, ApiMemberQuery, ApiMemberResult, Long, ApiMemberEntity, ApiMemberMapper, ApiMemberJpaRepository> {

}