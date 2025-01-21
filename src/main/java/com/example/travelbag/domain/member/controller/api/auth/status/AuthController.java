package com.example.travelbag.domain.member.controller.api.auth.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    // 생성자 주입
    public AuthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

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

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getAccessToken(Authentication authentication,
                                                              @Autowired OAuth2AuthorizedClientService authorizedClientService) {
        System.out.println("Access Token API 호출됨 아주 나이스");

        if (authentication == null) {
            System.out.println("Authentication 객체가 null입니다.");
            return ResponseEntity.status(401).body(Map.of("error", "User not authenticated"));
        }

        try {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            System.out.println("Client Registration ID: " + clientRegistrationId);

            OAuth2AuthorizedClient authorizedClient =
                    authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

            if (authorizedClient == null) {
                System.out.println("Authorized Client가 null입니다.");
                return ResponseEntity.status(500).body(Map.of("error", "Authorized client not found"));
            }

            if (authorizedClient.getAccessToken() == null) {
                System.out.println("Access Token이 null입니다.");
                return ResponseEntity.status(500).body(Map.of("error", "Access token not available"));
            }

            String kakaoAccessToken = authorizedClient.getAccessToken().getTokenValue();
            System.out.println("Access Token: " + kakaoAccessToken);

            return ResponseEntity.ok(Map.of("accessToken", kakaoAccessToken));
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Unexpected error occurred: " + e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Authorization header is missing or invalid"
            ));
        }

        // Bearer 접두어 제거 후 Access Token 추출
        String accessToken = authorizationHeader.replace("Bearer ", "").trim();
        String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";

        try {
            // 카카오 로그아웃 API 호출
            RestTemplate restTemplate = new RestTemplate();
            var headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            var request = new org.springframework.http.HttpEntity<>(headers);
            var response = restTemplate.postForEntity(kakaoLogoutUrl, request, String.class);

            // 로그아웃 성공 여부 확인
            if (response.getStatusCode().is2xxSuccessful()) {
                // Spring Security 세션 초기화
                SecurityContextHolder.clearContext();
                System.out.println("Kakao logout successful. Token: " + accessToken);

                return ResponseEntity.ok(Map.of(
                        "message", "Successfully logged out"
                ));
            } else {
                System.out.println("Kakao logout failed with status: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body(Map.of(
                        "error", "Kakao logout failed"
                ));
            }
        } catch (HttpStatusCodeException ex) {
            // 카카오 API에서 반환된 HTTP 상태 코드와 응답 메시지 처리
            System.out.println("Error during Kakao logout. Status: " + ex.getStatusCode() + ", Response: " + ex.getResponseBodyAsString());
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
                    "error", ex.getResponseBodyAsString()
            ));
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Unexpected error during Kakao logout: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error occurred"
            ));
        }
    }
}
