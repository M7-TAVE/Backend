package com.example.travelbag.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:http://localhost:5173/"; // 프론트엔드 홈으로 리다이렉트
    }
}