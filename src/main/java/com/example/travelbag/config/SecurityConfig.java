package com.example.travelbag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return userRequest -> {
            try {
                OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

                // 사용자 정보 디버깅
                System.out.println("OAuth2 User Info: " + oAuth2User.getAttributes());

                // 사용자 속성 가져오기
                Map<String, Object> attributes = oAuth2User.getAttributes();
                if (!attributes.containsKey("id")) {
                    throw new OAuth2AuthenticationException(
                            new OAuth2Error("missing_user_name_attribute", "User ID not found in response.", null));
                }

                return oAuth2User;

            } catch (OAuth2AuthenticationException ex) {
                System.err.println("Authentication Exception: " + ex.getError().getErrorCode());
                ex.printStackTrace();
                throw ex;
            }
        };
    }

}