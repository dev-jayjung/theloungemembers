package com.theloungemembers.core.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.api.entity.ApiMemberEntity;

public interface ApiMemberJpaRepository extends JpaRepository<ApiMemberEntity, Integer>, JpaSpecificationExecutor<ApiMemberEntity> {}