package com.example.travelbag.domain.souvenir.controller.api;

import com.example.travelbag.domain.souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.souvenir.dto.SouvenirsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "여행지", description = "여행지 관련 조회 API")
public interface SouvenirApi {

    @Operation(
            summary = "여행지별 추천 기념품 조회",    // 짧은 설명
            description = "여행지별 추천 기념품 목록을 조회합니다.",    // 상세 설명
            security = @SecurityRequirement(name = "bearerAuth")   // API 호출 시 인증 요구사항(추후 수정 예정)
    )
    // 반환 상태 코드 및 의미
    @ApiResponse(responseCode = "200", description = "기념품 목록 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    ResponseEntity<SouvenirsResponseDTO> getSouvenirsByLocation(
            @Parameter(description = "여행지 ID", required = true) Long location_id);
}