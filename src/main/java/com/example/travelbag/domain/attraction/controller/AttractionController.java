package com.example.travelbag.domain.attraction.controller;

import com.example.travelbag.domain.attraction.controller.api.AttractionApi;
import com.example.travelbag.domain.attraction.dto.AttractionResponseDTO;
import com.example.travelbag.domain.attraction.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttractionController implements AttractionApi {

    @Autowired
    private AttractionService attractionService;

    @Override
    public ResponseEntity<List<AttractionResponseDTO>> getAttractionsByLocation(@RequestParam(value = "location_id") Long location_id) {
        List<AttractionResponseDTO> airlines = attractionService.getAttractionsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

