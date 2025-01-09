package com.example.travelbag.domain.Souvenir.controller;

import com.example.travelbag.domain.Souvenir.controller.api.SouvenirApi;
import com.example.travelbag.domain.Souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.Souvenir.service.SouvenirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SouvenirController implements SouvenirApi {

    @Autowired
    private SouvenirService souvenirService;

    @Override
    public ResponseEntity<List<SouvenirResponseDTO>> getSouvenirsByLocation(@RequestParam(value = "location_id") Long location_id) {
        List<SouvenirResponseDTO> airlines = souvenirService.getSouvenirsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

