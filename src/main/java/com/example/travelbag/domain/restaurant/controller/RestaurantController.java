package com.example.travelbag.domain.restaurant.controller;

import com.example.travelbag.domain.restaurant.controller.api.RestaurantApi;
import com.example.travelbag.domain.restaurant.dto.RestaurantResponseDTO;
import com.example.travelbag.domain.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController implements RestaurantApi {

    @Autowired
    private RestaurantService restaurantService;

    @Override
    @GetMapping("/{location_id}")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByLocation(@PathVariable Long location_id) {
        List<RestaurantResponseDTO> airlines = restaurantService.getRestaurantsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

