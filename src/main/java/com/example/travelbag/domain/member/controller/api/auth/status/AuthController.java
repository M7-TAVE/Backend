package com.example.travelbag.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(Authentication authentication) {
        try {
            System.out.println("Starting /api/auth/status"); // 디버깅 로그 추가
            if (authentication == null) {
                System.out.println("Authentication is null");
                return ResponseEntity.status(401).body(Map.of("isAuthenticated", false));
            }

            System.out.println("Authentication isAuthenticated: " + authentication.isAuthenticated());

            OAuth2User user = (OAuth2User) authentication.getPrincipal();

            // 이메일과 닉네임 추출
            String email = user.getAttribute("email");
            String nickname = user.getAttribute("nickname");

            // null 값 처리 (기본값 설정)
            email = email != null ? email : "unknown-email";
            nickname = nickname != null ? nickname : "unknown-nickname";


            System.out.println("User email: " + user.getAttribute("email"));
            System.out.println("User nickname: " + user.getAttribute("nickname"));

            return ResponseEntity.ok(Map.of(
                    "isAuthenticated", true,
                    "email", user.getAttribute("email"),
                    "nickname", user.getAttribute("nickname")
            ));
        } catch (Exception e) {
            System.out.println("Error in /api/auth/status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
