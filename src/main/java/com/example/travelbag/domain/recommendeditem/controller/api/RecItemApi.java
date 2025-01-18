package com.example.travelbag.domain.recommendeditem.controller.api;

import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.recommendeditem.dto.RecItemRequestDto;
import com.example.travelbag.domain.recommendeditem.dto.RecItemResponseDto;
import com.example.travelbag.global.enums.ItemCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "추천 준비물 관리", description = "추천 준비물 관련 API")
public interface RecItemApi {

    @Operation(summary = "추천 준비물 조회(챙길것들 화면)", description = "회원 및 가방 정보를 기반으로 추천 준비물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = RecItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "회원 또는 가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<List<RecItemResponseDto>> getRecommendedItems(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "카테고리 ID", required = true) Long categoryId
    );

    @Operation(summary = "추천 준비물 추가(챙길것들 화면)", description = "추천 준비물을 가방에 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추가 성공",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<ItemResponseDto> addRecItemToBag(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "카테고리 ID", required = true) Long categoryId,
            @Parameter(description = "추천 준비물 요청 데이터", required = true) RecItemRequestDto recItemRequest
    );
}
