package com.example.travelbag.domain.feature.controller.api;

import com.example.travelbag.domain.feature.dto.bag_item_dto.BagItemResponseDto;
import com.example.travelbag.domain.feature.dto.recommended_item_dto.RecommendedItemResponseDto;
import com.example.travelbag.domain.feature.dto.template_item_dto.TemplateItemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "조회 기능", description = "조회 관련 기능 API")
public interface FeatureApi {

    @Operation(summary = "템플릿 아이템 목록 조회", description = "선택한 템플릿의 기본 아이템 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "템플릿 아이템 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = TemplateItemResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원/가방/템플릿"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원 ID", required = true),
            @Parameter(name = "bagId", description = "가방 ID", required = true),
            @Parameter(name = "templateId", description = "템플릿 ID", required = true)
    })
    ResponseEntity<TemplateItemResponseDto> getBagItem(@PathVariable("memberId") Long memberId,
                                                       @PathVariable Long bagId,
                                                       @PathVariable Long templateId);

    @Operation(summary = "가방 아이템 목록 조회", description = "특정 가방의 전체 아이템 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "가방 아이템 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = BagItemResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원/가방"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원 ID", required = true),
            @Parameter(name = "bagId", description = "가방 ID", required = true)
    })
    ResponseEntity<BagItemResponseDto> getBagItem(@PathVariable("memberId") Long memberId,
                                                  @PathVariable Long bagId);

    @Operation(summary = "추천 아이템 목록 조회", description = "사용자를 위한 추천 아이템 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "추천 아이템 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = RecommendedItemResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원/가방"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음")
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원 ID", required = true),
            @Parameter(name = "bagId", description = "가방 ID", required = true)
    })
    ResponseEntity<List<RecommendedItemResponseDto>> getRecommendedItems(@PathVariable("memberId") Long memberId,
                                                                         @PathVariable Long bagId);
}