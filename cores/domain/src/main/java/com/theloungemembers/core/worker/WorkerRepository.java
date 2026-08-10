package com.theloungemembers.core.worker;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.common.crud.AbstractBaseRepository;
import com.theloungemembers.core.worker.entity.WorkerEntity;
import com.theloungemembers.core.worker.mapper.WorkerMapper;
import com.theloungemembers.core.worker.repository.WorkerJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkerRepository extends AbstractBaseRepository<WorkerCommand, WorkerQuery, WorkerResult, Integer, WorkerEntity, WorkerMapper, WorkerJpaRepository> {
    public Optional<WorkerResult> selectByWorkerId(String workerId) {
        return mapper.selectByWorkerId(workerId);
    }
}