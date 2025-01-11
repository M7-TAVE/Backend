package com.example.travelbag.domain.location.controller;

import com.example.travelbag.domain.location.controller.api.LocationApi;
import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.CurrencyInfoDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import com.example.travelbag.domain.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController implements LocationApi {

    @Autowired
    private LocationService locationService;

    @Override
    @GetMapping()
    public ResponseEntity<List<LocationResponseDTO>> getLocations() {
        List<LocationResponseDTO> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @Override
    @GetMapping("/airline/{location_id}")
    public ResponseEntity<List<AirlineResponseDTO>> getAirlinesByLocation(@PathVariable Long location_id) {
        List<AirlineResponseDTO> airlines = locationService.getAirlinesByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }

    @Override
    @GetMapping("/exchange-rate/{location_id}")
    public ResponseEntity<CurrencyInfoDTO> getExchangeRate(@PathVariable Long location_id){
        CurrencyInfoDTO currency_info = locationService.getExchangeRate(location_id);
        return ResponseEntity.ok(currency_info);
    }

}