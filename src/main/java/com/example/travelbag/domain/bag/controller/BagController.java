package com.example.travelbag.domain.bag.controller;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.service.BagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bags")
@RequiredArgsConstructor
public class BagController {

    private final BagService bagService;

    @PostMapping("/temporary")
    public ResponseEntity<BagResponseDto> createTemporaryBag(@PathVariable Long memberId,
                                                             @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.createTemporaryBag(bagRequestDto, memberId);
        return ResponseEntity.ok(bag);
    }

    @GetMapping("/{bagId}")
    public ResponseEntity<BagResponseDto> getBag(@PathVariable Long memberId,
                                                 @PathVariable Long bagId) {
        BagResponseDto bag = bagService.getBag(bagId, memberId);
        return ResponseEntity.ok(bag);
    }

    @PatchMapping("/{bagId}/name")
    public ResponseEntity<BagResponseDto> updateBagName(@PathVariable Long memberId,
                                                        @PathVariable Long bagId,
                                                        @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.updateBagName(memberId, bagId, bagRequestDto);
        return ResponseEntity.ok(bag);
    }
}
