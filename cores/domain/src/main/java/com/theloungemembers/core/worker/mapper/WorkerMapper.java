package com.theloungemembers.core.worker.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.common.crud.BaseMapper;
import com.theloungemembers.core.worker.WorkerQuery;
import com.theloungemembers.core.worker.WorkerResult;

@Mapper
public interface WorkerMapper extends BaseMapper<WorkerQuery, WorkerResult, Integer> {
    Optional<WorkerResult> selectByWorkerId(String workerId);
}