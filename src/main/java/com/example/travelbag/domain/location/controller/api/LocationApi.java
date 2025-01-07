package com.example.travelbag.domain.location.controller.api;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.CurrencyInfoDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/location")
public interface LocationApi {

    @GetMapping()
    ResponseEntity<List<LocationResponseDTO>> getLocations();

    @GetMapping("/airline")
    ResponseEntity<List<AirlineResponseDTO>> getAirlinesByLocation(@RequestParam(value="location_id") Long location_id);

    @GetMapping("/exchange-rate")
    ResponseEntity<CurrencyInfoDTO> getExchangeRate(@RequestParam(value="location_id") Long location_id);

}