package com.example.travelbag.domain.item.mapper;

import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    // Dto -> Entity
    public static Item toItemEntity(ItemRequestDto itemRequestDto) {
        return Item.builder()
                .name(itemRequestDto.getName())
                .category(itemRequestDto.getCategory())
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
