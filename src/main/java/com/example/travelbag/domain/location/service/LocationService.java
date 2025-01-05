package com.example.travelbag.domain.location.service;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import com.example.travelbag.domain.location.entity.Location;
import com.example.travelbag.domain.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<LocationResponseDTO> getLocations() {
        return locationRepository.findAllByOrderByNameAsc()
                .stream()
                .map(LocationResponseDTO::of)
                .collect(Collectors.toList());
    }

    public List<AirlineResponseDTO> getAirlinesByLocation(Long location_id) {
        Location location = locationRepository.findById(location_id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        return location.getAirlines()
                .stream()
                .map(AirlineResponseDTO::of)
                .collect(Collectors.toList());  // AirlineResponseDTO를 통해 Airline 목록 반환
    }
}
