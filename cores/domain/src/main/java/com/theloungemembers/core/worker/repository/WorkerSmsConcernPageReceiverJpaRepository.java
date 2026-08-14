package com.theloungemembers.core.worker.repository;

import com.theloungemembers.core.worker.entity.WorkerSmsConcernPageReceiverEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkerSmsConcernPageReceiverJpaRepository extends JpaRepository<WorkerSmsConcernPageReceiverEntity, Long>, JpaSpecificationExecutor<WorkerSmsConcernPageReceiverEntity> {
}
