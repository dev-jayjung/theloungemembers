package com.theloungemembers.core.worker.repository;

import com.theloungemembers.core.worker.entity.WorkerSmsReceiverEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerSmsReceiverJpaRepository extends JpaRepository<WorkerSmsReceiverEntity, Long> {
}
