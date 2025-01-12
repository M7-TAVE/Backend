package com.example.travelbag.domain.location.dto;

import com.example.travelbag.domain.location.entity.Location;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyInfoDTO {
    private Long location_id;
    private String country;
    private String currency_unit;
    private double exchange_rate;

    // Location 객체와 환율 정보를 받아서 DTO 객체로 변환
    public static CurrencyInfoDTO of(Location location, double exchangeRate) {
        return CurrencyInfoDTO.builder()
                .location_id(location.getId())
                .country(location.getCountry())
                .currency_unit(location.getCurrency_unit())
                .exchange_rate(exchangeRate)
                .build();
    }
}
