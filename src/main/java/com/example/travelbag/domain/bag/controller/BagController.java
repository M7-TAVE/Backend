package com.example.travelbag.domain.bag.controller;

import com.example.travelbag.domain.bag.controller.api.BagApi;
import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.service.BagService;
import com.example.travelbag.global.enums.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member/{memberId}/bags")
@RequiredArgsConstructor
public class BagController implements BagApi {

    private final BagService bagService;

    // 가방 생성 (메인 페이지)
    @Override
    @PostMapping("/template/{templateId}")
    public ResponseEntity<BagResponseDto> createTemporaryBag(@PathVariable Long memberId,
                                                             @RequestParam Template template,
                                                             @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.createTemporaryBag(memberId, template, bagRequestDto);
        return ResponseEntity.ok(bag);
    }

    // 가방 전체 조회 (메인 페이지)
    @GetMapping
    @Override
    public ResponseEntity<List<BagResponseDto>> getBags(@PathVariable Long memberId) {
        List<BagResponseDto> bags = bagService.getBags(memberId);
        return ResponseEntity.ok(bags);
    }

    // 가방 조회 (챙길 것들)
    @GetMapping("/{bagId}")
    @Override
    public ResponseEntity<BagResponseDto> getBag(@PathVariable Long memberId,
                                                 @PathVariable Long bagId) {
        BagResponseDto bag = bagService.getBag(bagId, memberId);
        return ResponseEntity.ok(bag);
    }

    // 가방 이름 수정 (챙길 것들)
    @PatchMapping("/{bagId}/name")
    @Override
    public ResponseEntity<BagResponseDto> updateBagName(@PathVariable Long memberId,
                                                        @PathVariable Long bagId,
                                                        @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.updateBagName(memberId, bagId, bagRequestDto);
        return ResponseEntity.ok(bag);
    }

    // 가방 삭제 (챙길 것들)
    @DeleteMapping("/{bagId}")
    @Override
    public ResponseEntity<String> deleteBag(@PathVariable Long memberId,
                                            @PathVariable Long bagId) {
        bagService.deleteBag(memberId, bagId);
        return ResponseEntity.ok("해당 가방이 삭제되었습니다.");
    }
}
