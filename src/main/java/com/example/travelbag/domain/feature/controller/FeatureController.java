package com.example.travelbag.domain.feature.controller;

import com.example.travelbag.domain.feature.controller.api.FeatureApi;
import com.example.travelbag.domain.feature.dto.bag_item_dto.BagItemResponseDto;
import com.example.travelbag.domain.feature.dto.recommended_item_dto.RecommendedItemResponseDto;
import com.example.travelbag.domain.feature.dto.template_item_dto.TemplateItemResponseDto;
import com.example.travelbag.domain.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feature/member/{memberId}/bag/{bagId}")
public class FeatureController implements FeatureApi {

    private final FeatureService featureService;

    // 템플릿 아이템 목록
    @GetMapping("/template/{templateId}")
    public ResponseEntity<TemplateItemResponseDto> getBagItem(@PathVariable("memberId") Long memberId,
                                                              @PathVariable Long bagId,
                                                              @PathVariable Long templateId) {
        TemplateItemResponseDto response = featureService.getTemplateItem(memberId, bagId, templateId);
        return ResponseEntity.ok(response);
    }

    // 가방별 아이템 목록
    @GetMapping
    public ResponseEntity<BagItemResponseDto> getBagItem(@PathVariable("memberId") Long memberId,
                                                         @PathVariable Long bagId) {
        BagItemResponseDto bagItems = featureService.getBagItems(memberId, bagId);
        return ResponseEntity.ok(bagItems);
    }

    // 추천 아이템 목록
    @GetMapping("/recommended-items")
    public ResponseEntity<List<RecommendedItemResponseDto>> getRecommendedItems(@PathVariable("memberId") Long memberId,
                                                                                @PathVariable Long bagId) {
        List<RecommendedItemResponseDto> response = featureService.getRecommendedItems(memberId, bagId);
        return ResponseEntity.ok(response);
    }
}
