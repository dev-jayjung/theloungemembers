package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiSendCompanyInfoQuery;
import com.theloungemembers.core.api.ApiSendCompanyInfoResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiSendCompanyInfoMapper extends BaseMapper<ApiSendCompanyInfoQuery, ApiSendCompanyInfoResult, Long> {

}
