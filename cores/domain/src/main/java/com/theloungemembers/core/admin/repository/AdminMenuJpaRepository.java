package com.theloungemembers.core.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.admin.entity.AdminMenuEntity;

public interface AdminMenuJpaRepository extends JpaRepository<AdminMenuEntity, Integer>, JpaSpecificationExecutor<AdminMenuEntity> {}