package com.example.travelbag.domain.Souvenir.service;

import com.example.travelbag.domain.Souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.Souvenir.repository.SouvenirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SouvenirService {

    @Autowired
    private SouvenirRepository souvenirRepository;

    public List<SouvenirResponseDTO> getSouvenirsByLocation(Long location_id) {
        return souvenirRepository.findByLocationId(location_id)
                .stream()
                .map(SouvenirResponseDTO::of)
                .collect(Collectors.toList());
    }
}
