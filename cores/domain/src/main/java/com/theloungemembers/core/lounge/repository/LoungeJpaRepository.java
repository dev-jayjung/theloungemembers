package com.theloungemembers.core.lounge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.lounge.entity.LoungeEntity;

public interface LoungeJpaRepository extends JpaRepository<LoungeEntity, Integer>, JpaSpecificationExecutor<LoungeEntity> {}