package com.example.travelbag.domain.restaurant.dto;

import com.example.travelbag.domain.restaurant.entity.Restaurant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String signature;

    // Attraction 객체를 받아서 DTO 객체로 변환
    public static RestaurantResponseDTO of(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .signature(restaurant.getSignature())
                .build();
    }
}
