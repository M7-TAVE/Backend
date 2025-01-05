package com.example.travelbag.domain.bag.controller.api;

import com.example.travelbag.domain.member.dto.MemberResponseDto;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Tag(name = "가방관리", description = "가방 CRUD API")
public interface BagApi {

    @Operation(summary = "가방 조회", description = "가방 ID로 가방 정보를 조회합니다.")
    @ApiResponses( // getMemberId 메서드의 API 응답 상태 코드와 설명을 정의
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ResponseEntity<MemberResponseDto> getMembebag ID", required = true) Long bagId // memberId 파라미터가 요청에 필수로 포함되어야 하며, "회원 ID"라는 설명을 가진다고 정의
    );
}
