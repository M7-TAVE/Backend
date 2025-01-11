package com.example.travelbag.domain.souvenir.dto;

import com.example.travelbag.domain.souvenir.entity.Souvenir;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SouvenirResponseDTO {
    private Long id;
    private String name;
    private String url;

    // Souvenir 객체를 받아서 DTO 객체로 변환
    public static SouvenirResponseDTO of(Souvenir souvenir) {
        return SouvenirResponseDTO.builder()
                .id(souvenir.getId())
                .name(souvenir.getName())
                .url(souvenir.getUrl())
                .build();
    }
}
