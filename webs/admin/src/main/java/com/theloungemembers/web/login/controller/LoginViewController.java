package com.theloungemembers.web.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.theloungemembers.web.common.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginViewController {

    @GetMapping("/login")
    public String loginPage() {
        if (SecurityUtil.getCurrentWorker().isPresent()) {
            // 이미 로그인된 사용자는 메인 화면으로 리다이렉트
            return "redirect:/main";
        }

        return "login";
    }
}