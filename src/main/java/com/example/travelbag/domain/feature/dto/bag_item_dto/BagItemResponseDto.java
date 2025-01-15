package com.example.travelbag.domain.feature.dto.bag_item_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BagItemResponseDto {
    private Long bagId; // 가방 ID
    private List<CategoryItemDto> items; // 카테고리별 아이템 목록

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryItemDto {
        private Long categoryId; // 카테고리 ID
        private List<ItemDto> item; // 아이템 리스트

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ItemDto {
            private Long id; // 아이템 ID
            private String name; // 아이템 이름
            private boolean packed; // 포장 여부
        }
    }
}
