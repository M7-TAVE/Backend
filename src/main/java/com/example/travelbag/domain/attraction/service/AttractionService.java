package com.example.travelbag.domain.attraction.service;

import com.example.travelbag.domain.attraction.dto.AttractionResponseDTO;
import com.example.travelbag.domain.attraction.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    public List<AttractionResponseDTO> getAttractionsByLocation(Long location_id) {
        return attractionRepository.findByLocationId(location_id)
                .stream()
                .map(AttractionResponseDTO::of)
                .collect(Collectors.toList());
    }
}
