package com.example.travelbag.domain.location.controller.api;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.AirlinesResponseDTO;
import com.example.travelbag.domain.location.dto.CurrencyInfoDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "여행지", description = "여행지 CRUD API")
public interface LocationApi {

    @Operation(
            summary = "여행지 목록 조회",    // 짧은 설명
            description = "추천 여행지 목록을 조회합니다.",    // 상세 설명
            security = @SecurityRequirement(name = "bearerAuth")   // API 호출 시 인증 요구사항(추후 수정 예정)
    )
    // 반환 상태 코드 및 의미
    @ApiResponse(responseCode = "200", description = "여행지 목록 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    ResponseEntity<List<LocationResponseDTO>> getLocations();

    @Operation(
            summary = "여행지별 주요 항공사 조회",    // 짧은 설명
            description = "여행지별 주요 항공사 목록을 조회합니다.",    // 상세 설명
            security = @SecurityRequirement(name = "bearerAuth")   // API 호출 시 인증 요구사항(추후 수정 예정)
    )
    // 반환 상태 코드 및 의미
    @ApiResponse(responseCode = "200", description = "항공사 목록 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    ResponseEntity<AirlinesResponseDTO> getAirlinesByLocation(
            @Parameter(description = "여행지 ID", required = true) Long location_id);

    @Operation(
            summary = "여행지별 환율 조회",    // 짧은 설명
            description = "여행지별 환율과 국가명, 통화코드를 조회합니다.",    // 상세 설명
            security = @SecurityRequirement(name = "bearerAuth")   // API 호출 시 인증 요구사항
    )
    // 반환 상태 코드 및 의미
    @ApiResponse(responseCode = "200", description = "환율 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    ResponseEntity<CurrencyInfoDTO> getExchangeRate(
            @Parameter(description = "여행지 ID", required = true) Long location_id);

}