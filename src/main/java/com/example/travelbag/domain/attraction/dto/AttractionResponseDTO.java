package com.example.travelbag.domain.attraction.dto;

import com.example.travelbag.domain.attraction.entity.Attraction;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttractionResponseDTO {
    private Long id;
    private String name;
    private String url;

    // Attraction 객체를 받아서 DTO 객체로 변환
    public static AttractionResponseDTO of(Attraction attraction) {
        return AttractionResponseDTO.builder()
                .id(attraction.getId())
                .name(attraction.getName())
                .url(attraction.getUrl())
                .build();
    }
}
