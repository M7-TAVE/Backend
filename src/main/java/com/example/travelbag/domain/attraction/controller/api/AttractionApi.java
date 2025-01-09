package com.example.travelbag.domain.attraction.controller.api;

import com.example.travelbag.domain.attraction.dto.AttractionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Attraction", description = "관광지 관련 API")
@RequestMapping("/attraction")
public interface AttractionApi {

    @Operation(
            summary = "여행지별 추천 관광지 조회",    // 짧은 설명
            description = "여행지별 추천 관광지 목록을 조회합니다.",    // 상세 설명
            security = @SecurityRequirement(name = "bearerAuth")   // API 호출 시 인증 요구사항(추후 수정 예정)
    )
    // 반환 상태 코드 및 의미
    @ApiResponse(responseCode = "200", description = "관광지 목록 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @GetMapping()
    ResponseEntity<List<AttractionResponseDTO>> getAttractionsByLocation(@RequestParam(value="location_id") Long location_id);
}