package com.example.travelbag.global.security;

import com.example.travelbag.domain.member.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final String frontendDomain = "https://www.jionly.tech";
    private final String apiDomain = "https://api.jionly.tech";  // API 도메인 추가

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                response.sendRedirect(frontendDomain + "/");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendDomain));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "Origin",
                "Accept",
                "Referer",
                "User-Agent",
                "Access-Control-Allow-Origin"
        ));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channel -> channel
                        .requestMatchers("/**").requiresSecure())  // HTTPS 강제
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/",
                                "/login",
                                "/oauth2/**",
                                "/api/auth/status",
                                "/api/auth/login",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler())
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/authorization"))  // OAuth2 엔드포인트 명시적 설정
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(frontendDomain + "/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}