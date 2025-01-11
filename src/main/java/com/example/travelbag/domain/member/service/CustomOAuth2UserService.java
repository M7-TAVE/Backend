package com.example.travelbag.domain.member.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        System.out.println("CustomOAuth2UserService: loadUser method called"); // 호출 확인 로그

        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);
            System.out.println("oAuth2User: " + oAuth2User); // 반환값 확인
            System.out.println("oAuth2User Attributes: " + oAuth2User.getAttributes()); // 원본 Attributes 확인

            // 사용자 정보 매핑
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

            // email과 nickname 추출
            String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
            String nickname = properties != null ? (String) properties.get("nickname") : null;

            // 디버깅 로그 추가
            System.out.println("Extracted email: " + email);
            System.out.println("Extracted nickname: " + nickname);

            // 반환할 사용자 정보 생성
            Map<String, Object> mappedAttributes = new HashMap<>();
            mappedAttributes.put("email", email);
            mappedAttributes.put("nickname", nickname);

            // DefaultOAuth2User 반환
            return new DefaultOAuth2User(
                    oAuth2User.getAuthorities(),
                    mappedAttributes, // 새로 매핑된 사용자 정보
                    "email" // 기본 식별자 (이 경우 email)
            );
        } catch (Exception e) {
            System.out.println("Error in loadUser: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
