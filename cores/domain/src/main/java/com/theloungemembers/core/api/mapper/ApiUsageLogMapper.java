package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiUsageLogQuery;
import com.theloungemembers.core.api.ApiUsageLogResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * API 이용 로그 쿼리
 */
@Mapper
public interface ApiUsageLogMapper extends BaseMapper<ApiUsageLogQuery, ApiUsageLogResult, Long> {

    List<ApiUsageLogResult> selectDpFailList(Long uid);

    OffsetDateTime selectDpLatestRegDate(@Param("apiCodes") List<String> apiCodes);

}
