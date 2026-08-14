package com.theloungemembers.core.worker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerSmsReceiverService {

    private final WorkerSmsReceiverRepository workerSmsReceiverRepository;

    @Transactional(readOnly = true)
    public List<WorkerSmsReceiverResult> getListByConcernPageCode(String concernPageCode) {
        return workerSmsReceiverRepository.selectListByConcernPageCode(concernPageCode);
    }
}
