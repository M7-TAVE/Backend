package com.example.travelbag.domain.recommendeditem.controller;

import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.recommendeditem.controller.api.RecItemApi;
import com.example.travelbag.domain.recommendeditem.dto.RecItemRequestDto;
import com.example.travelbag.domain.recommendeditem.dto.RecItemResponseDto;
import com.example.travelbag.domain.recommendeditem.service.RecItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/{memberId}/bags/{bagId}/recommendeditem/category")
public class RecItemController implements RecItemApi {

    private final RecItemService recItemService;

    // 추천 준비물 조회 API
    @Override
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<RecItemResponseDto>> getRecommendedItems(@PathVariable Long memberId,
                                                                        @PathVariable Long bagId,
                                                                        @PathVariable Long categoryId) {
        List<RecItemResponseDto> recommendedItems = recItemService.getRecommendedItemsByCategory(memberId, bagId, categoryId);
        return ResponseEntity.ok(recommendedItems);
    }

    // 추천 준비물 추가 API
    @Override
    @PostMapping("/{categoryId}")
    public ResponseEntity<ItemResponseDto> addRecItemToBag(@PathVariable Long memberId,
                                                           @PathVariable Long bagId,
                                                           @PathVariable Long categoryId,
                                                           @RequestBody RecItemRequestDto recItemRequest) {
        ItemResponseDto response = recItemService.addRecItemToBag(memberId, bagId, categoryId, recItemRequest);
        return ResponseEntity.ok(response);
    }
}

