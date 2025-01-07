package com.example.travelbag.domain.recommendeditem.controller;

import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.recommendeditem.controller.api.RecItemApi;
import com.example.travelbag.domain.recommendeditem.dto.RecItemRequestDto;
import com.example.travelbag.domain.recommendeditem.dto.RecItemResponseDto;
import com.example.travelbag.domain.recommendeditem.service.RecItemService;
import com.example.travelbag.global.enums.ItemCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/{memberId}")
public class RecItemController implements RecItemApi {

    private final RecItemService recItemService;

    // 추천 준비물 조회 API
    @Override
    @GetMapping("/recommendeditems")
    public ResponseEntity<List<RecItemResponseDto>> getRecommendedItems(@RequestParam Long memberId,
                                                                        @RequestParam Long bagId,
                                                                        @RequestParam ItemCategory category) {
        List<RecItemResponseDto> recommendedItems = recItemService.getRecommendedItemsByCategory(memberId, bagId, category);
        return ResponseEntity.ok(recommendedItems);
    }

    // 추천 준비물 추가 API
    @Override
    @PostMapping("/bags/{bagId}/recommendeditem")
    public ResponseEntity<ItemResponseDto> addRecItemToBag(@PathVariable Long bagId,
                                                           @RequestParam Long memberId,
                                                           @RequestBody RecItemRequestDto recItemRequest) {
        ItemResponseDto response = recItemService.addRecItemToBag(memberId, bagId, recItemRequest.getName(), recItemRequest.getCategory());
        return ResponseEntity.ok(response);
    }
}

