package com.theloungemembers.core.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.theloungemembers.core.admin.AdminMenuQuery;
import com.theloungemembers.core.admin.AdminMenuResult;
import com.theloungemembers.core.common.crud.BaseMapper;

@Mapper
public interface AdminMenuMapper extends BaseMapper<AdminMenuQuery, AdminMenuResult, Long> {
    public List<AdminMenuResult> selectMainMenuList(AdminMenuQuery query);

    public List<AdminMenuResult> selectSubMenuList(AdminMenuQuery query);

    public List<AdminMenuResult> selectBookmarkList(AdminMenuQuery query);

    public AdminMenuResult selectMenuTitle(String linkUrl);
}