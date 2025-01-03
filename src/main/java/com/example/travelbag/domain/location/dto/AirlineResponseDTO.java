package com.example.travelbag.domain.location.dto;

import com.example.travelbag.domain.location.entity.Airline;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponseDTO {
    private Long id;
    private String name;
    private String image_url;
    private String url;

    // Airline 객체를 받아서 DTO 객체로 변환
    public static AirlineResponseDTO of(Airline airline) {
        return AirlineResponseDTO.builder()
                .id(airline.getId())
                .name(airline.getName())
                .image_url(airline.getImage_url())
                .url(airline.getUrl())
                .build();
    }
}
