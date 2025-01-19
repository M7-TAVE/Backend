package com.example.travelbag.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final String base_url = "https://jiwon.d3kcu00ykarmab.amplifyapp.com/";

    @GetMapping("/")
    public String home() {
        return "redirect:" + base_url; // 프론트엔드 홈으로 리다이렉트
    }
}