package com.example.travelbag.domain.item.controller.api;

import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
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

@Tag(name = "물품 관리", description = "물품 관련 API")
public interface ItemApi {

    @Operation(summary = "물품 생성(챙길것들 화면)", description = "가방에 새로운 물품을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<ItemResponseDto> createItem(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "카테고리 ID", required = true) Long itemCategoryId,
            @Parameter(description = "물품 정보", required = true) ItemRequestDto itemRequestDto
    );

    @Operation(summary = "물품 조회(챙길것들 화면)", description = "가방에 있는 물품을 카테고리별로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<List<ItemResponseDto>> getItems(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "카테고리 ID", required = true) Long itemCategoryId
    );

    @Operation(summary = "물품 이름 수정(챙길것들 화면)", description = "가방의 특정 물품 이름을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "물품을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "물품 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<ItemResponseDto> updateItemName(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "물품 ID", required = true) Long itemId,
            @Parameter(description = "수정할 물품 정보", required = true) ItemRequestDto itemRequestDto
    );

    @Operation(summary = "물품 삭제(챙길것들 화면)", description = "가방에서 특정 물품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(schema = @Schema(type = "string", example = "삭제되었습니다."))),
            @ApiResponse(responseCode = "404", description = "물품을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "물품 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<String> deleteItem(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "물품 ID", required = true) Long itemId
    );

    @Operation(summary = "is_packed 토글", description = "is_packed 토글")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(type = "string", example = "변경되었습니다"))),
            @ApiResponse(responseCode = "404", description = "물품을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "물품 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<ItemResponseDto> togglePacked(
            @Parameter(description = "회원 ID", required = true) String kakaoId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "물품 ID", required = true) Long itemId
    );
}
