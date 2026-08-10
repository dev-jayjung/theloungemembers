package com.theloungemembers.core.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.theloungemembers.core.worker.entity.WorkerEntity;

public interface WorkerJpaRepository extends JpaRepository<WorkerEntity, Integer>, JpaSpecificationExecutor<WorkerEntity> {}