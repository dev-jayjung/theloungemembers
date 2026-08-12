package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiAccessibleIpQuery;
import com.theloungemembers.core.api.ApiAccessibleIpResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * API 접속 허용 IP 쿼리
 */
@Mapper
public interface ApiAccessibleIpMapper extends BaseMapper<ApiAccessibleIpQuery, ApiAccessibleIpResult, Long> {

    Long selectUidByIpAddress(String ipAddress);

}
