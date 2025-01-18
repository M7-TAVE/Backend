package com.example.travelbag.domain.feature.service;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.feature.dto.bag_item_dto.BagItemResponseDto;
import com.example.travelbag.domain.feature.dto.recommended_item_dto.RecommendedItemResponseDto;
import com.example.travelbag.domain.feature.dto.template_item_dto.TemplateItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.item.repository.ItemRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.domain.recommendeditem.entity.RecItem;
import com.example.travelbag.domain.recommendeditem.repository.RecItemRepository;
import com.example.travelbag.global.enums.ItemCategory;
import com.example.travelbag.global.enums.Template;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/member/{memberId}")
public class FeatureService {

    private final MemberRepository memberRepository;
    private final BagRepository bagRepository;
    private final ItemRepository itemRepository;
    private final RecItemRepository recItemRepository;

    // 템플릿별 아이템 목록
    @Transactional(readOnly = true)
    public TemplateItemResponseDto getTemplateItem(String kakaoId, Long bagId, Long templateId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        Template template = Template.fromId(templateId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));

        // RecItem 데이터를 미리 조회하여 Map으로 변환
        Map<ItemCategory, Map<String, Long>> recItemsMap = Arrays.stream(ItemCategory.values())
                .collect(Collectors.toMap(
                        category -> category,
                        category -> recItemRepository.findAllByCategory(category).stream()
                                .collect(Collectors.toMap(
                                        RecItem::getName,
                                        RecItem::getId
                                ))
                ));

        List<TemplateItemResponseDto.CategoryItemDto> categoryItems = Arrays.stream(ItemCategory.values())
                .map(category -> {
                    List<String> defaultItems = template.getDefaultItems().getOrDefault(category, new ArrayList<>());
                    Map<String, Long> categoryRecItems = recItemsMap.get(category);

                    List<TemplateItemResponseDto.CategoryItemDto.ItemDto> itemDtos = defaultItems.stream()
                            .map(itemName -> TemplateItemResponseDto.CategoryItemDto.ItemDto.builder()
                                    .id(categoryRecItems.getOrDefault(itemName, null))  // RecItem의 ID 매핑
                                    .name(itemName)
                                    .packed(false)
                                    .build())
                            .collect(Collectors.toList());

                    return TemplateItemResponseDto.CategoryItemDto.builder()
                            .categoryId(category.getId())
                            .items(itemDtos)
                            .build();
                })
                .collect(Collectors.toList());

        return TemplateItemResponseDto.builder()
                .templateId(templateId)
                .items(categoryItems)
                .build();
    }

    // 가방별 아이템 목록
    @Transactional(readOnly = true)
    public BagItemResponseDto getBagItems(String kakaoId, Long bagId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // 가방에 속한 모든 아이템 조회
        List<Item> items = itemRepository.findAllByBagId(bagId);

        // 카테고리별로 아이템 그룹화
        Map<ItemCategory, List<Item>> itemsByCategory = items.stream()
                .collect(Collectors.groupingBy(Item::getCategory));

        // CategoryItemDto 리스트 생성
        List<BagItemResponseDto.CategoryItemDto> categoryItems = Arrays.stream(ItemCategory.values())
                .map(category -> {
                    List<Item> otherCategoryItems = itemsByCategory.getOrDefault(category, new ArrayList<>());

                    // ItemDto 리스트 생성
                    List<BagItemResponseDto.CategoryItemDto.ItemDto> itemDtos = otherCategoryItems.stream()
                            .map(item -> BagItemResponseDto.CategoryItemDto.ItemDto.builder()
                                    .id(item.getId())
                                    .name(item.getName())
                                    .packed(item.isPacked())
                                    .build())
                            .collect(Collectors.toList());

                    // CategoryItemDto 생성
                    return BagItemResponseDto.CategoryItemDto.builder()
                            .categoryId(category.getId())
                            .item(itemDtos)
                            .build();
                })
                .collect(Collectors.toList());

        // 최종 응답 DTO 생성
        return BagItemResponseDto.builder()
                .bagId(bagId)
                .items(categoryItems)
                .build();
    }

    // 추천 아이템 목록
    @Transactional(readOnly = true)
    public List<RecommendedItemResponseDto> getRecommendedItems(String kakaoId, Long bagId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // 모든 추천 아이템 조회
        List<RecItem> recItems = recItemRepository.findAll();

        // 카테고리별로 아이템 그룹화
        Map<ItemCategory, List<RecItem>> itemsByCategory = recItems.stream()
                .collect(Collectors.groupingBy(RecItem::getCategory));

        // 각 카테고리별로 ResponseDto 생성
        return Arrays.stream(ItemCategory.values())
                .map(category -> {
                    // 해당 카테고리의 추천 아이템 목록 가져오기
                    List<RecItem> categoryItems = itemsByCategory.getOrDefault(category, new ArrayList<>());

                    // ItemDto 리스트 생성
                    List<RecommendedItemResponseDto.ItemDto> itemDtos = categoryItems.stream()
                            .map(item -> RecommendedItemResponseDto.ItemDto.builder()
                                    .id(item.getId())
                                    .name(item.getName())
                                    .build())
                            .collect(Collectors.toList());

                    // RecommendedItemResponseDto 생성
                    return RecommendedItemResponseDto.builder()
                            .categoryId(category.getId())
                            .items(itemDtos)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
