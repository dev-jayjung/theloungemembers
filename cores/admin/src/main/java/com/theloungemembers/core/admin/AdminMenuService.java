package com.theloungemembers.core.admin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.theloungemembers.core.api.ApiMenuCommand;
import com.theloungemembers.core.common.crud.AbstractBaseService;
import com.theloungemembers.core.util.AssertUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuService extends AbstractBaseService<ApiMenuCommand, AdminMenuQuery, AdminMenuResult, Long> {
    private final AdminMenuRepository adminMenuRepository;

    @Cacheable(value = "adminMenuListCache", key = "'all'")
    // @Cacheable(value = "adminMenuListCache", key = "'#query?.workerId'")
    public List<AdminMenuResult> getMenuList(AdminMenuQuery query) {
        AssertUtil.notNull(query);

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
    @Cacheable(value = "adminBookmarkCache", key = "'all'")
    // @Cacheable(value = "adminBookmarkCache", key = "#query?.workerId")
    public List<AdminMenuResult> getBookmarkList(AdminMenuQuery query) {
        AssertUtil.notNull(query);

        return adminMenuRepository.selectBookmarkList(query);
    }

    @Cacheable(value = "menuTitleCache", key = "#linkUrl", unless = "#result == null")
    public AdminMenuResult getMenuTitle(String linkUrl) {
        AssertUtil.notNull(linkUrl);

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