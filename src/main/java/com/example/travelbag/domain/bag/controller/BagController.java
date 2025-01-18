package com.example.travelbag.domain.bag.controller;

import com.example.travelbag.domain.bag.controller.api.BagApi;
import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.service.BagService;
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
    public ResponseEntity<BagResponseDto> createTemporaryBag(@PathVariable("memberId") String kakaoId,
                                                             @PathVariable Long templateId,
                                                             @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.createTemporaryBag(kakaoId, templateId, bagRequestDto);
        return ResponseEntity.ok(bag);
    }

    // 가방 전체 조회 (메인 페이지)
    @GetMapping
    @Override
    public ResponseEntity<List<BagResponseDto>> getBags(@PathVariable("memberId") String kakaoId) {
        List<BagResponseDto> bags = bagService.getBags(kakaoId);
        return ResponseEntity.ok(bags);
    }

    // 가방 조회 (챙길 것들)
    @GetMapping("/{bagId}")
    @Override
    public ResponseEntity<BagResponseDto> getBag(@PathVariable("memberId") String kakaoId,
                                                 @PathVariable Long bagId) {
        BagResponseDto bag = bagService.getBag(bagId, kakaoId);
        return ResponseEntity.ok(bag);
    }

    // 가방 이름 수정 (챙길 것들)
    @PatchMapping("/{bagId}/name")
    @Override
    public ResponseEntity<BagResponseDto> updateBagName(@PathVariable("memberId") String kakaoId,
                                                        @PathVariable Long bagId,
                                                        @RequestBody BagRequestDto bagRequestDto) {
        BagResponseDto bag = bagService.updateBagName(kakaoId, bagId, bagRequestDto);
        return ResponseEntity.ok(bag);
    }

    // 가방 삭제 (챙길 것들)
    @DeleteMapping("/{bagId}")
    @Override
    public ResponseEntity<String> deleteBag(@PathVariable("memberId") String kakaoId,
                                            @PathVariable Long bagId) {
        bagService.deleteBag(kakaoId, bagId);
        return ResponseEntity.ok("해당 가방이 삭제되었습니다.");
    }

    // is_temporary 토글 API
    @PatchMapping("/{bagId}/toggle-temporary")
    public ResponseEntity<BagResponseDto> toggleTemporary(@PathVariable("memberId") String kakaoId,
                                                          @PathVariable Long bagId) {
        BagResponseDto bag = bagService.toggleTemporary(kakaoId, bagId);
        return ResponseEntity.ok(bag);
    }
}
