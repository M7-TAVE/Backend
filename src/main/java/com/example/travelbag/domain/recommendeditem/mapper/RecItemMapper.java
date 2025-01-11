package com.example.travelbag.domain.recommendeditem.mapper;

import com.example.travelbag.domain.recommendeditem.dto.RecItemResponseDto;
import com.example.travelbag.domain.recommendeditem.entity.RecItem;

import java.util.List;
import java.util.stream.Collectors;

public class RecItemMapper {

    // Entity -> Dto
    public static RecItemResponseDto toRecItemDto(RecItem recommendedItem) {
        return RecItemResponseDto.builder()
                .id(recommendedItem.getId())
                .name(recommendedItem.getName())
                .category(recommendedItem.getCategory())
                .build();
    }

    public static List<RecItemResponseDto> toRecItemDtoList(List<RecItem> recommendedItems) {
        return recommendedItems.stream().map(RecItemMapper::toRecItemDto).collect(Collectors.toList());
    }
}
