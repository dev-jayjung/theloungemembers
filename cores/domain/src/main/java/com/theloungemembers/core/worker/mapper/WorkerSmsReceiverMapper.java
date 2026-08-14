package com.theloungemembers.core.worker.mapper;

import com.theloungemembers.core.worker.WorkerSmsReceiverResult;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkerSmsReceiverMapper {

    List<WorkerSmsReceiverResult> selectListByConcernPageCode(@Param("concernPageCode") String concernPageCode);

}
