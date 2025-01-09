package com.example.travelbag.domain.Restaurant.service;

import com.example.travelbag.domain.Restaurant.dto.RestaurantResponseDTO;
import com.example.travelbag.domain.Restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<RestaurantResponseDTO> getRestaurantsByLocation(Long location_id) {
        return restaurantRepository.findByLocationId(location_id)
                .stream()
                .map(RestaurantResponseDTO::of)
                .collect(Collectors.toList());
    }
}
