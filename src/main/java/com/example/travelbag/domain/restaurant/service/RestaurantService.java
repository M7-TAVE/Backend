package com.example.travelbag.domain.restaurant.service;

import com.example.travelbag.domain.restaurant.dto.RestaurantResponseDTO;
import com.example.travelbag.domain.restaurant.dto.RestaurantsResponseDTO;
import com.example.travelbag.domain.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantsResponseDTO getRestaurantsByLocation(Long location_id) {
        List<RestaurantResponseDTO> restaurantResponseDTOS = restaurantRepository.findByLocationId(location_id)
                .stream()
                .map(RestaurantResponseDTO::of)
                .collect(Collectors.toList());

        return RestaurantsResponseDTO.of(location_id, restaurantResponseDTOS);
    }
}
