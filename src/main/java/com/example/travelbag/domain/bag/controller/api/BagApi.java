package com.example.travelbag.domain.bag.controller.api;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.global.enums.Template;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "가방 관리", description = "가방 관련 API")
public interface BagApi {

    @Operation(summary = "가방 생성(홈 화면)", description = "템플릿의 플러스 버튼을 클릭하여 가방 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = BagResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "회원 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<BagResponseDto> createTemporaryBag(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @Parameter(description = "템플릿 ID", required = true) Long templateId,
            @Parameter(description = "가방 요청 정보", required = true) BagRequestDto bagRequestDto
    );

    @Operation(summary = "가방 전체 조회(홈 화면)", description = "회원의 모든 가방을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = BagResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "회원 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<List<BagResponseDto>> getBags(
            @Parameter(description = "회원 ID", required = true) Long memberId
    );

    @Operation(summary = "가방 상세 조회(챙길것들 화면)", description = "특정 가방을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = BagResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<BagResponseDto> getBag(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @Parameter(description = "가방 ID", required = true) Long bagId
    );

    @Operation(summary = "가방 이름 수정(챙길것들 화면)", description = "특정 가방의 이름을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = BagResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<BagResponseDto> updateBagName(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @Parameter(description = "가방 ID", required = true) Long bagId,
            @Parameter(description = "수정할 가방 정보", required = true) BagRequestDto bagRequestDto
    );

    @Operation(summary = "가방 삭제(챙길것들 화면)", description = "특정 가방을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공",
                    content = @Content(schema = @Schema(type = "string", example = "가방이 삭제되었습니다."))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<String> deleteBag(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @Parameter(description = "가방 ID", required = true) Long bagId
    );

    @Operation(summary = "is_temporary 토글", description = "is_temporary 토글")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(schema = @Schema(type = "string", example = "변경되었습니다."))),
            @ApiResponse(responseCode = "404", description = "가방을 찾을 수 없음",
                    content = @Content(schema = @Schema(type = "string", example = "가방 ID를 찾을 수 없습니다.")))
    })
    ResponseEntity<BagResponseDto> toggleTemporary(
            @Parameter(description = "회원 ID", required = true) Long memberId,
            @Parameter(description = "가방 ID", required = true) Long bagId
    );
}
