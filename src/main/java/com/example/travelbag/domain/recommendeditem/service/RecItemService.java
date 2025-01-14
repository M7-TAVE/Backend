package com.example.travelbag.domain.recommendeditem.service;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.item.mapper.ItemMapper;
import com.example.travelbag.domain.item.repository.ItemRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.domain.recommendeditem.dto.RecItemRequestDto;
import com.example.travelbag.domain.recommendeditem.dto.RecItemResponseDto;
import com.example.travelbag.domain.recommendeditem.entity.RecItem;
import com.example.travelbag.domain.recommendeditem.mapper.RecItemMapper;
import com.example.travelbag.domain.recommendeditem.repository.RecItemRepository;
import com.example.travelbag.global.enums.ItemCategory;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecItemService {

    private final MemberRepository memberRepository;
    private final BagRepository bagRepository;
    private final ItemRepository itemRepository;
    private final RecItemRepository recItemRepository;

    // 추천 준비물 카테고리 별로 조회 API
    @Transactional(readOnly = true)
    public List<RecItemResponseDto> getRecommendedItemsByCategory(Long memberId, Long bagId, Long categoryId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        ItemCategory category = ItemCategory.fromId(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        List<RecItem> recItems = recItemRepository.findAllByCategory(category);
        return RecItemMapper.toRecItemDtoList(recItems);
    }

    // 추천 준비물에서 해당 가방에 물품 추가
    @Transactional
    public ItemResponseDto addRecItemToBag(Long memberId, Long bagId, Long categoryId, RecItemRequestDto recItemRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        ItemCategory category = ItemCategory.fromId(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        boolean isDuplicate = itemRepository.existsByBagIdAndNameAndCategory(
                bagId,
                recItemRequest.getName(),
                category
        );

        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_ITEM);
        }

        Item newItem = Item.builder()
                .name(recItemRequest.getName())
                .category(category)
                .bag(bag)
                .isPacked(false)
                .build();

        Item savedItem = itemRepository.save(newItem);

        return ItemMapper.toItemDto(savedItem);
    }
}
