package com.example.travelbag.global.security;

import com.example.travelbag.domain.member.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                // Vite 프론트엔드로 리다이렉트
                response.sendRedirect("https://www.jionly.tech");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()) // CORS 설정
                .and()
                .csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/",
                                "/login",
                                "/oauth2/**",
                                "/api/auth/status",
                                "/api/auth/login",
                                "/api-docs/**",  // Swagger API Docs 허용
                                "/swagger-ui/**",   // Swagger UI 허용
                                "/swagger-ui/index.html"  // Swagger HTML 허용
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONS 요청 허용
                        .requestMatchers(HttpMethod.PATCH, "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oauth2AuthenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("https://www.jionly.tech/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://www.jionly.tech", "http://localhost:5174"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
