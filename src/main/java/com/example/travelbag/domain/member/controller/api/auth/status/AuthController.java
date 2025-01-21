package com.example.travelbag.domain.member.controller.api.auth.status;

import com.example.travelbag.domain.member.controller.api.AuthApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

    private final OAuth2AuthorizedClientService authorizedClientService;

    // 생성자 주입
    public AuthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/status")
    @Override
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
            String kakaoId = user.getAttribute("id"); // KakaoId
            String email = user.getAttribute("email");
            String nickname = user.getAttribute("nickname");

            // null 값 처리 (기본값 설정)
            email = email != null ? email : "unknown-email";
            nickname = nickname != null ? nickname : "unknown-nickname";

            System.out.println("User KakaoId: " + kakaoId);
            System.out.println("User email: " + user.getAttribute("email"));
            System.out.println("User nickname: " + user.getAttribute("nickname"));

            return ResponseEntity.ok(Map.of(
                    "isAuthenticated", true,
                    "kakaoId", kakaoId,
                    "email", email,
                    "nickname", nickname
            ));
        } catch (Exception e) {
            System.out.println("Error in /api/auth/status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/logout")
    @Override
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=== Logout Process Started ===");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Current Authentication: " +
                (authentication != null ? authentication.getName() : "anonymous"));
        System.out.println("Session ID: " + request.getSession(false) != null ?
                request.getSession().getId() : "no session");

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            SecurityContextHolder.clearContext();
            System.out.println("Logout successful - session invalidated");
        } else {
            System.out.println("No authentication found to logout");
        }

        Map<String, Object> authInfo = Map.of(
                "isAuthenticated", false,
                "kakaoId", null,
                "email", null,
                "nickname", null
        );

        System.out.println("=== Logout Process Completed ===");
        return ResponseEntity.ok(Map.of(
                "message", "Logged out successfully",
                "authInfo", authInfo
        ));
    }
}