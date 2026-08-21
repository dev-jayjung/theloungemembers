package com.theloungemembers.core.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.admin.AdminMenuBookmarkQuery;
import com.theloungemembers.core.admin.AdminMenuBookmarkResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface AdminMenuBookmarkMapper extends BaseMapper<AdminMenuBookmarkQuery, AdminMenuBookmarkResult, Long> {
}