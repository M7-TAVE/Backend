package com.example.travelbag.domain.souvenir.dto;

import com.example.travelbag.domain.souvenir.entity.Souvenir;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SouvenirsResponseDTO {
    private Long location_id;
    private List<SouvenirResponseDTO> souvenirs;

    // Souvenir 객체를 받아서 DTO 객체로 변환
    public static SouvenirsResponseDTO of(Long location_id, List<SouvenirResponseDTO> souvenirs) {
        return SouvenirsResponseDTO.builder()
                .location_id(location_id)
                .souvenirs(souvenirs)
                .build();
    }
}
