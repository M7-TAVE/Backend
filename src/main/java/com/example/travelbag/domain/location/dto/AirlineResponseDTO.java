package com.example.travelbag.domain.location.dto;

import com.example.travelbag.domain.location.entity.Airline;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponseDTO {
    private Long id;
    private String name;
    private String url;

    // Airline 객체를 받아서 DTO 객체로 변환
    public static AirlineResponseDTO of(Airline airline) {
        return AirlineResponseDTO.builder()
                .id(airline.getId())
                .name(airline.getName())
                .url(airline.getUrl())
                .build();
    }
}
