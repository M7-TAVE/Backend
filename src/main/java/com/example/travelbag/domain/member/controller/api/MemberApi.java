package com.example.travelbag.domain.member.controller.api;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 관리", description = "회원 관련 API")
public interface MemberApi {

    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(schema = @Schema(type = "string", example = "홍길동님이 회원가입 되었습니다."))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않음",
                    content = @Content(schema = @Schema(type = "string", example = "잘못된 요청입니다.")))
    })
    ResponseEntity<String> createMember(
            @Parameter(description = "회원가입 요청 데이터", required = true) MemberRequestDto memberRequestDto
    );

    @Operation(summary = "회원조회", description = "회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원조회 성공",
                    content = @Content(schema = @Schema(type = "string", example = "회원조회 되었습니다."))),
            @ApiResponse(responseCode = "400", description = "요청 데이터가 유효하지 않음",
                    content = @Content(schema = @Schema(type = "string", example = "잘못된 요청입니다.")))
    })
    ResponseEntity<MemberResponseDto> getMember(
            @Parameter(description = "회원 ID", required = true) Long memberId
    );
}
