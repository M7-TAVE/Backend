package com.example.travelbag.domain.recommendeditem.dto;

import com.example.travelbag.global.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecItemResponseDto {

    private Long id;

    private String name;

    private ItemCategory category;

}
