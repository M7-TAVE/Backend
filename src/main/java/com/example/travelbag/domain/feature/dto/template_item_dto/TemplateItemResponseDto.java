package com.example.travelbag.domain.feature.dto.template_item_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateItemResponseDto {
    private Long templateId;
    private List<CategoryItemDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryItemDto {
        private Long categoryId;
        private List<ItemDto> items;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ItemDto {
            private Long id;
            private String name;
            private boolean packed;
        }
    }
}