package com.theloungemembers.web.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.theloungemembers.core.admin.AdminMenuQuery;
import com.theloungemembers.core.admin.AdminMenuResult;
import com.theloungemembers.core.admin.AdminMenuService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.web.admin.dto.AdminMenuResponse;
import com.theloungemembers.web.common.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

/**
 * ADMIN 메뉴 관리 컨트롤러
 */
@Controller
@RequestMapping("/admin-menus")
@RequiredArgsConstructor
public class AdminMenuViewController {

    private final AdminMenuService adminMenuService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping
    public String getMenuList(@RequestParam(defaultValue = "ALL") String bookmark, Model model) {

        AdminMenuQuery query = AdminMenuQuery.builder()
                // .workerId(SecurityUtil.getWorkerId())
                .isAdmin(true)
                .build();

        boolean isBookmarkTab = "BOOKMARK".equals(bookmark);

        List<AdminMenuResult> results = isBookmarkTab
                ? adminMenuService.getBookmarkList(query)
                : adminMenuService.getMenuList(query);

        model.addAttribute("menuList", modelMapperHelper.mapList(results, AdminMenuResponse.class));
        model.addAttribute("isBookmarkTab", isBookmarkTab); // 즐겨찾기 탭 여부도 함께 넘기면 UI 제어가 쉬워집니다.

        return "fragments/left_menu :: leftMenu";
    }
}
