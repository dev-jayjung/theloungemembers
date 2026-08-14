package com.theloungemembers.core.worker;

import com.theloungemembers.core.worker.mapper.WorkerSmsReceiverMapper;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WorkerSmsReceiverRepository {

    private final WorkerSmsReceiverMapper workerSmsReceiverMapper;

    public List<WorkerSmsReceiverResult> selectListByConcernPageCode(String concernPageCode) {
        return workerSmsReceiverMapper.selectListByConcernPageCode(concernPageCode);
    }
}
