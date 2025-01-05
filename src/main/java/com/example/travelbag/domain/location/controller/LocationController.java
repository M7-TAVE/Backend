package com.example.travelbag.domain.location.controller;

import com.example.travelbag.domain.location.controller.api.LocationApi;
import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import com.example.travelbag.domain.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController implements LocationApi {

    @Autowired
    private LocationService locationService;

    @Override
    public ResponseEntity<List<LocationResponseDTO>> getLocations() {
        List<LocationResponseDTO> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @Override
    public ResponseEntity<List<AirlineResponseDTO>> getAirlinesByLocation(@RequestParam(value="location_id") Long location_id) {
        List<AirlineResponseDTO> airlines = locationService.getAirlinesByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}