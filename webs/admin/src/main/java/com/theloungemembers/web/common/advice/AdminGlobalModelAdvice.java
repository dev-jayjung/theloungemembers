package com.theloungemembers.web.common.advice;

import java.util.List;

import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.theloungemembers.core.admin.AdminMenuQuery;
import com.theloungemembers.core.admin.AdminMenuResult;
import com.theloungemembers.core.admin.AdminMenuService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.web.main.dto.AdminMenuResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice(annotations = Controller.class, basePackages = "com.theloungemembers.web")
@RequiredArgsConstructor
public class AdminGlobalModelAdvice {

    private final AdminMenuService adminMenuService;
    private final ModelMapperHelper modelMapperHelper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @ModelAttribute
    public void addMenuAttributes(Model model, Authentication authentication, HttpServletRequest request) {
        String uri = request.getRequestURI();

        if (Strings.CS.equals("/error", uri)) {
            return;
        }

        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);

        if (handler instanceof HandlerMethod handlerMethod) {
            // 컨트롤러 클래스에 @RestController가 붙어있으면 스킵
            if (handlerMethod.getBeanType().isAnnotationPresent(RestController.class)) {
                return;
            }
        }

        // AJAX (application/json) 요청 시 스킵
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return;
        }

         if (Strings.CS.equals("/main", uri)) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            String workerId = authentication.getName();
//            boolean isAdmin = authentication.getAuthorities().stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            AdminMenuQuery query = new AdminMenuQuery();
//            query.setWorkerId(workerId);
            query.setAdmin(true);

            // 모든 어드민 요청 시 LNB/즐겨찾기 메뉴 자동 주입
            List<AdminMenuResult> mainMenuList = adminMenuService.getMenuList(query);
            List<AdminMenuResult> bookmarkList = adminMenuService.getBookmarkList(query);

            model.addAttribute("mainMenuList", modelMapperHelper.mapList(mainMenuList, AdminMenuResponse.class));
            model.addAttribute("bookmarkList", modelMapperHelper.mapList(bookmarkList, AdminMenuResponse.class));
    //    }
        } else {
            AdminMenuQuery query = new AdminMenuQuery();
//          query.setWorkerId(workerId);
            query.setAdmin(true);

            AdminMenuResult result = adminMenuService.getMenuTitle(uri);
            if (result != null) {
                model.addAttribute("mainTitle", result.getMainTitle());
                model.addAttribute("subTitle", result.getSubTitle());
            }

//            adminMenuService.getMenuList(query)
//                .stream()
//                .flatMap(main -> main.getSubMenuList()
//                        .stream()
//                        .map(sub -> new AbstractMap.SimpleEntry<>(main, sub))) // main과 sub를 쌍으로 묶음
////                .sorted(Comparator.comparingInt((AbstractMap.SimpleEntry<AdminMenuResult, AdminMenuResult> pair) ->
////                        pair.getValue().getLinkUrl() != null ? pair.getValue().getLinkUrl().length() : 0).reversed())
//                .filter(pair -> isMatchUri(uri, pair.getValue().getLinkUrl()))
//                .findFirst()
//                .ifPresent(pair -> {
//                    model.addAttribute("mainTitle", pair.getKey().getTitle());
//                    model.addAttribute("subTitle", pair.getValue().getTitle());
//                });
        }
    }

    private boolean isMatchUri(String requestUri, String menuLinkUrl) {
        if (menuLinkUrl == null) {
            return false;
        }

        // 정확히 일치하는 경우 (예: /api-members/new)
        if (Strings.CS.equals(requestUri, menuLinkUrl)) {
            return true;
        }

        // menuLinkUrl이 /api-members 형태일 때, /api-members/123 처럼 하위 경로로 들어오는 케이스 매칭
        // menuLinkUrl + "/**" 패턴 검사

        if (pathMatcher.match(menuLinkUrl + "/**", requestUri)) {
            return true;
        }

        // DB menuLinkUrl 자체가 /api-members/{id} 형태인 경우
        return pathMatcher.match(menuLinkUrl, requestUri);
    }
}