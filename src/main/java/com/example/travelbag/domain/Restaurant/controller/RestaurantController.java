package com.example.travelbag.domain.Restaurant.controller;

import com.example.travelbag.domain.Restaurant.controller.api.RestaurantApi;
import com.example.travelbag.domain.Restaurant.dto.RestaurantResponseDTO;
import com.example.travelbag.domain.Restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController implements RestaurantApi {

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByLocation(@RequestParam(value = "location_id") Long location_id) {
        List<RestaurantResponseDTO> airlines = restaurantService.getRestaurantsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

