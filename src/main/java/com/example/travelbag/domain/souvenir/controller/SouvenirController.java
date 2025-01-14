package com.example.travelbag.domain.souvenir.controller;

import com.example.travelbag.domain.souvenir.controller.api.SouvenirApi;
import com.example.travelbag.domain.souvenir.dto.SouvenirResponseDTO;
import com.example.travelbag.domain.souvenir.dto.SouvenirsResponseDTO;
import com.example.travelbag.domain.souvenir.service.SouvenirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/souvenir")
public class SouvenirController implements SouvenirApi {

    @Autowired
    private SouvenirService souvenirService;

    @Override
    @GetMapping("/{location_id}")
    public ResponseEntity<SouvenirsResponseDTO> getSouvenirsByLocation(@PathVariable Long location_id) {
        SouvenirsResponseDTO souvenirs = souvenirService.getSouvenirsByLocation(location_id);
        return ResponseEntity.ok(souvenirs);
    }
}

