package com.example.travelbag.domain.location.service;

import com.example.travelbag.domain.location.dto.AirlineResponseDTO;
import com.example.travelbag.domain.location.dto.AirlinesResponseDTO;
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

    public AirlinesResponseDTO getAirlinesByLocation(Long location_id) {
        Location location = locationRepository.findById(location_id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        List<AirlineResponseDTO> airlineResponseDTOs = location.getAirlines()
                .stream()
                .map(AirlineResponseDTO::of)
                .collect(Collectors.toList());

        return AirlinesResponseDTO.of(location_id, airlineResponseDTOs);
    }

    public CurrencyInfoDTO getExchangeRate(Long location_id) {
        Location location = locationRepository.findById(location_id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        String currency_unit = location.getCurrency_unit();

        double exchange_rate = 0.0;
        String searchDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        int request_count = 0;    // 30회 이상 예외 발생으로 호출 시, 강제 종료 후 default 값 반환

        do {
            try {
                if (request_count > 30) {
                    exchange_rate = 1200.00;
                    break;
                }
                request_count++;
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
