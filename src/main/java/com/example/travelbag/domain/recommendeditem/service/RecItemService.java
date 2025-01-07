package com.example.travelbag.domain.recommendeditem.service;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.item.mapper.ItemMapper;
import com.example.travelbag.domain.item.repository.ItemRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
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
    @Transactional
    public List<RecItemResponseDto> getRecommendedItems(Long memberId, Long bagId, ItemCategory category) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        List<RecItem> recItemList = recItemRepository.findAllByCategory(category);

        return RecItemMapper.toRecItemDtoList(recItemList);
    }

    // 추천 준비물에서 해당 가방에 물품 추가
    @Transactional
    public ItemResponseDto addRecItemToBag(Long memberId, Long bagId, String name, ItemCategory category) {
        // 멤버 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 가방 검증
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // 가방 소유자 검증
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        // 이미 추가된 물품인지 확인
        boolean isDuplicate = itemRepository.existsByBagIdAndNameAndCategory(
                bagId,
                name,
                category
        );

        if (isDuplicate) {
            throw new CustomException(ErrorCode.DUPLICATE_ITEM);
        }

        // 새로운 물품 생성
        Item newItem = Item.builder()
                .name(name)
                .category(category)
                .bag(bag)
                .isPacked(false)
                .build();

        Item savedItem = itemRepository.save(newItem);

        return ItemMapper.toItemDto(savedItem);
    }
}
