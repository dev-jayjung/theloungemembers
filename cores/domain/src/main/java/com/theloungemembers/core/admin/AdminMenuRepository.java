package com.theloungemembers.core.admin;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.theloungemembers.core.admin.entity.AdminMenuEntity;
import com.theloungemembers.core.admin.mapper.AdminMenuMapper;
import com.theloungemembers.core.admin.repository.AdminMenuJpaRepository;
import com.theloungemembers.core.common.crud.AbstractBaseRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminMenuRepository extends AbstractBaseRepository<AdminMenuCommand, AdminMenuQuery, AdminMenuResult, Integer, AdminMenuEntity, AdminMenuMapper, AdminMenuJpaRepository> {

    public List<AdminMenuResult> selectMainMenuList(AdminMenuQuery query) {
        return mapper.selectMainMenuList(query);
    }

    public List<AdminMenuResult> selectSubMenuList(AdminMenuQuery query) {
        return mapper.selectSubMenuList(query);
    }

    public List<AdminMenuResult> selectBookmarkList(AdminMenuQuery query) {
        return mapper.selectBookmarkList(query);
    }

    public AdminMenuResult selectMenuTitle(String linkUrl) {
        return mapper.selectMenuTitle(linkUrl);
    }
}