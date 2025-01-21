package com.example.travelbag.domain.member.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Map;

@Tag(name = "인증 관리", description = "OAuth2 인증, 로그아웃 및 로그인 상태 관련 API")
public interface AuthApi {

    @Operation(summary = "인증 상태 확인", description = "현재 사용자의 인증 상태와 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 상태 반환 성공",
                    content = @Content(schema = @Schema(type = "object", example = """
                        {
                          "isAuthenticated": true,
                          "kakaoId": "12345",
                          "email": "user@example.com",
                          "nickname": "nickname"
                        }
                        """))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
                    content = @Content(schema = @Schema(type = "object", example = """
                        {
                          "isAuthenticated": false
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생",
                    content = @Content(schema = @Schema(type = "object", example = """
                        {
                          "error": "Unexpected error occurred"
                        }
                        """)))
    })
    ResponseEntity<Map<String, Object>> getAuthStatus(
            @Parameter(description = "사용자 인증 정보", required = true) Authentication authentication
    );

    @Operation(summary = "로그아웃", description = "현재 사용자를 로그아웃 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(type = "object", example = """
                        {
                          "message": "Logged out successfully",
                          "authInfo": {
                            "isAuthenticated": false,
                            "kakaoId": null,
                            "email": null,
                            "nickname": null
                          }
                        }
                        """))),
            @ApiResponse(responseCode = "500", description = "로그아웃 중 서버 오류 발생",
                    content = @Content(schema = @Schema(type = "object", example = """
                        {
                          "error": "Unexpected error occurred"
                        }
                        """))),
    })
    ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response);
}
