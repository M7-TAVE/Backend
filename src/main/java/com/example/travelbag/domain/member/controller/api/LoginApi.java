package com.example.travelbag.domain.member.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;

@Tag(name = "로그인 관리", description = "로그인 성공 및 실패 처리 API")
public interface LoginApi {

    @Operation(summary = "로그인 성공 처리", description = "OAuth2 인증이 성공하면 사용자 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(type = "string", example = """
                        로그인 성공! 사용자 정보: {attributes}
                        """)))
    })
    String loginSuccess(
            @Parameter(description = "사용자 인증 정보", required = true) Authentication authentication
    );

    @Operation(summary = "로그인 실패 처리", description = "OAuth2 인증이 실패하면 에러 메시지를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 실패",
                    content = @Content(schema = @Schema(type = "string", example = "로그인 실패! 다시 시도하세요.")))
    })
    String loginFailure();
}