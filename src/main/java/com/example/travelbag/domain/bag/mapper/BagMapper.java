package com.example.travelbag.domain.bag.mapper;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.entity.Bag;

public class BagMapper {
    // Dto -> Entity
    public static Bag toBagEntity(BagRequestDto bagRequestDto) {
        return Bag.builder()
                .name(bagRequestDto.getName())
                .build();
    }

    // Entity -> Dto
}
