package com.example.travelbag.domain.attraction.service;

import com.example.travelbag.domain.attraction.dto.AttractionResponseDTO;
import com.example.travelbag.domain.attraction.dto.AttractionsResponseDTO;
import com.example.travelbag.domain.attraction.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    public AttractionsResponseDTO getAttractionsByLocation(Long location_id) {
        List<AttractionResponseDTO> attractionResponseDTOs = attractionRepository.findByLocationId(location_id)
                .stream()
                .map(AttractionResponseDTO::of)
                .collect(Collectors.toList());

        return AttractionsResponseDTO.of(location_id, attractionResponseDTOs);
    }
}
