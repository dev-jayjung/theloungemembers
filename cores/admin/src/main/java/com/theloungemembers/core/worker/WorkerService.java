package com.theloungemembers.core.worker;

import org.springframework.stereotype.Service;

import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.exception.CommonErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService extends AbstractBaseService<WorkerCommand, WorkerQuery, WorkerResult, Integer> {

    private final WorkerRepository workerRepository;

    public WorkerResult getByWorkerId(String workerId) {
        return workerRepository.selectByWorkerId(workerId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));
    }
}