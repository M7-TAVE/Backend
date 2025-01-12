package com.example.travelbag.domain.attraction.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionsResponseDTO {
    private Long location_id;
    private List<AttractionResponseDTO> attractions;

    public static AttractionsResponseDTO of(Long location_id, List<AttractionResponseDTO> attractions) {
        return AttractionsResponseDTO.builder()
                .location_id(location_id)
                .attractions(attractions)
                .build();
    }
}