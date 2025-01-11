package com.example.travelbag.domain.location.controller;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.CurrencyInfoDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import com.example.travelbag.domain.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController{

    @Autowired
    private LocationService locationService;

    public ResponseEntity<List<LocationResponseDTO>> getLocations() {
        List<LocationResponseDTO> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    public ResponseEntity<List<AirlineResponseDTO>> getAirlinesByLocation(@RequestParam(value="location_id") Long location_id) {
        List<AirlineResponseDTO> airlines = locationService.getAirlinesByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }

    public ResponseEntity<CurrencyInfoDTO> getExchangeRate(@RequestParam(value="location_id") Long location_id){
        CurrencyInfoDTO currency_info = locationService.getExchangeRate(location_id);
        return ResponseEntity.ok(currency_info);
    }

}