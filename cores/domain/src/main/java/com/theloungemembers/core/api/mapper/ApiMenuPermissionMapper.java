package com.theloungemembers.core.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.api.ApiMenuPermissionQuery;
import com.theloungemembers.core.api.ApiMenuPermissionResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface ApiMenuPermissionMapper extends BaseMapper<ApiMenuPermissionQuery, ApiMenuPermissionResult, Integer> {
    boolean existsApiMenuPermission(String accountId, String apiCode);

    List<ApiMenuPermissionResult> selectListByAccountId(String accountId);
}