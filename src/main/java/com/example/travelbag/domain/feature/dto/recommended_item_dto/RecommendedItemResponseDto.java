package com.example.travelbag.domain.feature.dto.recommended_item_dto;

import com.example.travelbag.domain.feature.dto.bag_item_dto.BagItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedItemResponseDto {
    private Long categoryId;
    private List<ItemDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDto {
        private Long id;
        private String name;
    }
}
