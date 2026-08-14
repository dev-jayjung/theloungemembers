package com.theloungemembers.core.worker.repository;

import com.theloungemembers.core.worker.entity.WorkerSmsConcernPageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkerSmsConcernPageJpaRepository extends JpaRepository<WorkerSmsConcernPageEntity, Long>, JpaSpecificationExecutor<WorkerSmsConcernPageEntity> {
}
