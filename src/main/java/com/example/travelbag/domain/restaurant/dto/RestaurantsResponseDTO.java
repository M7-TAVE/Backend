package com.example.travelbag.domain.restaurant.dto;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.AirlinesResponseDTO;
import com.example.travelbag.domain.restaurant.entity.Restaurant;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantsResponseDTO {
    private Long location_id;
    private List<RestaurantResponseDTO> restaurants;

    public static RestaurantsResponseDTO of(Long location_id, List<RestaurantResponseDTO> restaurants) {
        return RestaurantsResponseDTO.builder()
                .location_id(location_id)
                .restaurants(restaurants)
                .build();
    }
}
