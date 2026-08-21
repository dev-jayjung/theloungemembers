package com.theloungemembers.core.api.mapper;

import com.theloungemembers.core.api.ApiSendCompanyDataQuery;
import com.theloungemembers.core.api.ApiSendCompanyDataResult;
import com.theloungemembers.core.common.crud.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiSendCompanyDataMapper extends BaseMapper<ApiSendCompanyDataQuery, ApiSendCompanyDataResult, Long> {

}
