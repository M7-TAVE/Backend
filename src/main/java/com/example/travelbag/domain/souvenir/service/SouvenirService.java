package com.example.travelbag.domain.souvenir.service;

import com.example.travelbag.domain.souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.souvenir.dto.SouvenirsResponseDTO;
import com.example.travelbag.domain.souvenir.repository.SouvenirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SouvenirService {

    @Autowired
    private SouvenirRepository souvenirRepository;

    public SouvenirsResponseDTO getSouvenirsByLocation(Long location_id) {
        List<SouvenirResponseDTO> souvenirResponseDTOS = souvenirRepository.findByLocationId(location_id)
                .stream()
                .map(SouvenirResponseDTO::of)
                .collect(Collectors.toList());

        return SouvenirsResponseDTO.of(location_id, souvenirResponseDTOS);
    }
}
