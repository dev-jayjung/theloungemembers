package com.theloungemembers.core.admin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.theloungemembers.core.api.ApiMenuCommand;
import com.theloungemembers.core.common.crud.AbstractBaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuService extends AbstractBaseService<ApiMenuCommand, AdminMenuQuery, AdminMenuResult, Integer> {
    private final AdminMenuRepository adminMenuRepository;

    @Cacheable(value = "adminMenuListCache", key = "'all'")
    public List<AdminMenuResult> getMenuList(AdminMenuQuery query) {
        List<AdminMenuResult> mainMenuList = adminMenuRepository.selectMainMenuList(query);
        List<AdminMenuResult> subMenuList = adminMenuRepository.selectSubMenuList(query);

        Map<String, List<AdminMenuResult>> subMenuMap = subMenuList.stream()
                .filter(sub -> sub.getParentCode() != null)
                .collect(Collectors.groupingBy(AdminMenuResult::getParentCode));

        for (AdminMenuResult mainMenu : mainMenuList) {
            List<AdminMenuResult> list = subMenuMap.getOrDefault(mainMenu.getMenuCode(), Collections.emptyList());
            mainMenu.setSubMenuList(list);
        }

        return mainMenuList;
    }

// TODO 로그인 기능 완료되면 추후 로그인 아이디 기준으로 캐시 적용
//    @Cacheable(value = "adminBookmarkCache", key = "#query.workerId")
    @Cacheable(value = "adminBookmarkCache", key = "'all'")
    public List<AdminMenuResult> getBookmarkList(AdminMenuQuery query) {
        return adminMenuRepository.selectBookmarkList(query);
    }

    @Cacheable(value = "menuTitleCache", key = "#root.args[0]", unless = "#result == null")
    public AdminMenuResult getMenuTitle(String linkUrl) {
        return adminMenuRepository.selectMenuTitle(linkUrl);
    }

//    @CacheEvict(value = "adminMenuTreeCache", allEntries = true)
//    public void clearMenuTreeCache() {
//        log.info(">>>> [Cache Evict] 메뉴 트리 캐시 전체 삭제");
//    }
//
//    @CacheEvict(value = "adminBookmarkCache", key = "#workerId")
//    public void clearBookmarkCache(String workerId) {
//        log.info(">>>> [Cache Evict] adminSeq={} 의 즐겨찾기 캐시 삭제", adminSeq);
//    }
}