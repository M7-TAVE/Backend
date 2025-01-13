package com.example.travelbag.domain.item.mapper;

import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.global.enums.ItemCategory;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    // Dto -> Entity
    public static Item toItemEntity(Long categoryId, ItemRequestDto itemRequestDto) {
        ItemCategory category = ItemCategory.fromId(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        return Item.builder()
                .name(itemRequestDto.getName())
                .category(category)
                .isPacked(false)
                .build();
    }

    // Entity -> Dto
    public static ItemResponseDto toItemDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .isPacked(item.isPacked())
                .build();
    }

    // Entity -> Dto
    public static List<ItemResponseDto> toItemDtos(List<Item> items) {
        return items.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }
}
