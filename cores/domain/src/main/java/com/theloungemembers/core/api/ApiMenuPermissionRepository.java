package com.theloungemembers.core.api;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.api.entity.ApiMenuPermissionEntity;
import com.theloungemembers.core.api.mapper.ApiMenuPermissionMapper;
import com.theloungemembers.core.api.repository.ApiMenuPermissionJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;
import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ApiMenuPermissionRepository extends AbstractBaseRepository<ApiMenuPermissionCommand, ApiMenuPermissionQuery, ApiMenuPermissionResult, Integer, ApiMenuPermissionEntity, ApiMenuPermissionMapper, ApiMenuPermissionJpaRepository> {

    public List<ApiMenuPermissionResult> selectApiMenuPermissionList(String accountId) {
        AssertUtil.notNull(accountId);

        return mapper.selectListByAccountId(accountId);
    }
}