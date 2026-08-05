package com.theloungemembers.core.lounge;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.common.crud.AbstractBaseRepository;
import com.theloungemembers.core.lounge.entity.LoungeEntity;
import com.theloungemembers.core.lounge.mapper.LoungeMapper;
import com.theloungemembers.core.lounge.repository.LoungeJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LoungeRepository extends AbstractBaseRepository<LoungeCommand, LoungeQuery, LoungeResult, Integer, LoungeEntity, LoungeMapper, LoungeJpaRepository> {

}