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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@RequiredArgsConstructor
public class SecurityConfig {

    private final String base_url = "https://m7-frontend.vercel.app"; // http://localhost:5174
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                // Vite 프론트엔드로 리다이렉트
                response.sendRedirect(base_url + "/");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()  // CORS 설정 추가
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/",
                                "/login",
                                "/oauth2/**",
                                "/api/auth/status",
                                "/api/auth/login",
                                "/api/**",  // API 엔드포인트 추가
                                "/api-docs/**",  // Swagger API Docs 허용
                                "/swagger-ui/**",   // Swagger UI 허용
                                "/swagger-ui/index.html"  // Swagger HTML 허용
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(base_url + "/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(base_url)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}