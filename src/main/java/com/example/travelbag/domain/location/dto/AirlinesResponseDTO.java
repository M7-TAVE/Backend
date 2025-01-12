package com.example.travelbag.domain.location.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlinesResponseDTO {
    private Long location_id;
    private List<AirlineResponseDTO> airlines;

    public static AirlinesResponseDTO of(Long location_id, List<AirlineResponseDTO> airlines) {
        return AirlinesResponseDTO.builder()
                .location_id(location_id)
                .airlines(airlines)
                .build();
    }
}