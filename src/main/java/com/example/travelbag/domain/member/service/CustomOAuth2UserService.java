package com.example.travelbag.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.domain.member.entity.Member;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository; // 의존

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());  // 디버깅용 로그

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String id = String.valueOf(attributes.get("id"));

        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        String nickname = properties != null ? (String) properties.get("nickname") : null;

        System.out.println("Mapped id: " + id);  // 디버깅용 로그
        System.out.println("Mapped email: " + email);  // 디버깅용 로그
        System.out.println("Mapped nickname: " + nickname);  // 디버깅용 로그

        // H2 데이터베이스에 저장
        Optional<Member> existingMember = memberRepository.findByKakaoId(id);

        if (existingMember.isEmpty()) {
            Member newMember = Member.builder()
                    .kakaoId(id)
                    .email(email)
                    .nickname(nickname)
                    .build();
            memberRepository.save(newMember);
        }

        Map<String, Object> mappedAttributes = new HashMap<>();
        mappedAttributes.put("id", id);
        mappedAttributes.put("email", email);
        mappedAttributes.put("nickname", nickname);

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                mappedAttributes,
                "email"  // nameAttributeKey를 "email"로 설정
        );
    }
}