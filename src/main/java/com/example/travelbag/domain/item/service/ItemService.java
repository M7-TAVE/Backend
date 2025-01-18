package com.example.travelbag.domain.item.service;

import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.item.mapper.ItemMapper;
import com.example.travelbag.domain.item.repository.ItemRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.global.enums.ItemCategory;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final MemberRepository memberRepository;
    private final BagRepository bagRepository;
    private final ItemRepository itemRepository;

    // 물품 생성 API
    @Transactional
    public ItemResponseDto createItem(String kakaoId, Long bagId, Long categoryId, ItemRequestDto itemRequestDto) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        ItemCategory category = ItemCategory.fromId(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Item item = Item.builder()
                .name(itemRequestDto.getName())
                .category(category)
                .bag(bag)
                .isPacked(false)
                .build();

        itemRepository.save(item);

        return ItemMapper.toItemDto(item);
    }

    // 카테고리별 물품 조회 API
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getItems(String kakaoId, Long bagId, Long categoryId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!member.getId().equals(bag.getMember().getId())) {
            throw new CustomException(ErrorCode.BAG_NOT_OWNED_BY_MEMBER);
        }

        // categoryId로 ItemCategory 찾기
        ItemCategory category = ItemCategory.fromId(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        List<Item> items = itemRepository.findByBagIdAndCategoryId(bagId, category);

        return ItemMapper.toItemDtos(items);
    }

    // 물품 이름 수정 API
    @Transactional
    public ItemResponseDto updateItemName(String kakaoId, Long bagId, Long itemId, ItemRequestDto itemRequestDto) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if (!member.getId().equals(item.getBag().getMember().getId())){
            throw new CustomException(ErrorCode.UPDATE_PERMISSION_DENIED);
        }

        item.updateItemInfo(itemRequestDto);
        return ItemMapper.toItemDto(item);
    }

    // 물품 삭제 API
    @Transactional
    public void deleteItem(String kakaoId, Long bagId, Long itemId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if (!member.getId().equals(item.getBag().getMember().getId())){
            throw new CustomException(ErrorCode.DELETE_PERMISSION_DENIED);
        }

        itemRepository.delete(item);
    }

    // is_packed 토글 API
    @Transactional
    public ItemResponseDto togglePacked(String kakaoId, Long bagId, Long itemId) {
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if (!member.getId().equals(item.getBag().getMember().getId())){
            throw new CustomException(ErrorCode.DELETE_PERMISSION_DENIED);
        }

        item.togglePacked();

        return ItemMapper.toItemDto(item);
    }
}
