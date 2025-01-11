package com.example.travelbag.domain.attraction.controller;

import com.example.travelbag.domain.attraction.controller.api.AttractionApi;
import com.example.travelbag.domain.attraction.dto.AttractionResponseDTO;
import com.example.travelbag.domain.attraction.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attraction")
public class AttractionController implements AttractionApi {

    @Autowired
    private AttractionService attractionService;

    @Override
    @GetMapping("/{location_id}")
    public ResponseEntity<List<AttractionResponseDTO>> getAttractionsByLocation(@PathVariable Long location_id) {
        List<AttractionResponseDTO> airlines = attractionService.getAttractionsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

