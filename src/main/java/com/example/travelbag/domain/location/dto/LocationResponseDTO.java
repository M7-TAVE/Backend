package com.example.travelbag.domain.location.dto;

import com.example.travelbag.domain.location.entity.Location;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {
    private Long id;
    private String name;

    // Location 객체를 받아서 DTO 객체로 변환
    public static LocationResponseDTO of(Location location) {
        return LocationResponseDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }
}
