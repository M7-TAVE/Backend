package com.example.travelbag.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
//        return "redirect:https://m7-frontend.vercel.app/"; // 프론트엔드 홈으로 리다이렉트
        return "redirect:http://localhost:5174/";
    }
}