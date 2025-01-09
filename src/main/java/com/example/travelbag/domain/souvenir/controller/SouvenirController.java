package com.example.travelbag.domain.souvenir.controller;

import com.example.travelbag.domain.souvenir.controller.api.SouvenirApi;
import com.example.travelbag.domain.souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.souvenir.service.SouvenirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/souvenir")
public class SouvenirController implements SouvenirApi {

    @Autowired
    private SouvenirService souvenirService;

    @Override
    @GetMapping("/{location_id}")
    public ResponseEntity<List<SouvenirResponseDTO>> getSouvenirsByLocation(@PathVariable Long location_id) {
        List<SouvenirResponseDTO> airlines = souvenirService.getSouvenirsByLocation(location_id);
        return ResponseEntity.ok(airlines);
    }
}

