package com.example.travelbag.domain.member.controller.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
        // 로그인 성공 시 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return "로그인 성공! 사용자 정보: " + oAuth2User.getAttributes();
    }

    @GetMapping("/login-failure")
    public String loginFailure() {
        // 로그인 실패 시 메시지 표시
        return "로그인 실패! 다시 시도하세요.";
    }
}