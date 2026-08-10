package com.theloungemembers.web.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MainViewController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/main/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }
}