package com.example.travelbag.domain.location.service;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.CurrencyInfoDTO;
import com.example.travelbag.domain.location.dto.LocationResponseDTO;
import com.example.travelbag.domain.location.entity.Location;
import com.example.travelbag.domain.location.repository.LocationRepository;
import com.example.travelbag.domain.location.utils.ExchangeRateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public CurrencyInfoDTO getExchangeRate(Long location_id) {
        Location location = locationRepository.findById(location_id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        String currency_unit = location.getCurrency_unit();

        double exchange_rate = 0.0;
        String searchDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        do {
            try {
                exchange_rate = ExchangeRateUtils.fetchExchangeRate(searchDate, currency_unit);
            } catch (Exception e) {
                // 예외 발생 시 하루 전 날짜로 변경
                System.out.println(e.getMessage());
                searchDate = ExchangeRateUtils.getPreviousDate(searchDate);
            }
        } while (exchange_rate == 0);

        return CurrencyInfoDTO.of(location, exchange_rate);
    }
}
