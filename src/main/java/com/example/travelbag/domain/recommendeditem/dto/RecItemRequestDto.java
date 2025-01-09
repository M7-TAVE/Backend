package com.example.travelbag.domain.recommendeditem.dto;

import com.example.travelbag.global.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecItemRequestDto {

    private String name;

    private ItemCategory category;
}
