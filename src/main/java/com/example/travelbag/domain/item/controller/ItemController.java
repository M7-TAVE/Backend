package com.example.travelbag.domain.item.controller;

import com.example.travelbag.domain.item.controller.api.ItemApi;
import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/{memberId}/bags/{bagId}/item")
public class ItemController implements ItemApi {

    private final ItemService itemService;

    // 물품 생성 (챙길 것들)
    @PostMapping("/category/{categoryId}")
    @Override
    public ResponseEntity<ItemResponseDto> createItem(@PathVariable("memberId") String kakaoId,
                                                      @PathVariable Long bagId,
                                                      @PathVariable Long categoryId,
                                                      @RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto item = itemService.createItem(kakaoId, bagId, categoryId, itemRequestDto);
        return ResponseEntity.ok(item);
    }

    // 카테고리별 물품 조회 (챙길 것들)
    @GetMapping("/category/{categoryId}")
    @Override
    public ResponseEntity<List<ItemResponseDto>> getItems(@PathVariable("memberId") String kakaoId,
                                                          @PathVariable Long bagId,
                                                          @PathVariable Long categoryId) {
        List<ItemResponseDto> items = itemService.getItems(kakaoId, bagId, categoryId);
        return ResponseEntity.ok(items);
    }

    // 물품 이름 수정 (챙길 것들)
    @PatchMapping("{itemId}/name")
    @Override
    public ResponseEntity<ItemResponseDto> updateItemName(@PathVariable("memberId") String kakaoId,
                                                          @PathVariable Long bagId,
                                                          @PathVariable Long itemId,
                                                          @RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto itemResponseDto = itemService.updateItemName(kakaoId, bagId, itemId, itemRequestDto);
        return ResponseEntity.ok(itemResponseDto);
    }

    // 물품 삭제 (챙길 것들)
    @DeleteMapping("/{itemId}")
    @Override
    public ResponseEntity<String> deleteItem(@PathVariable("memberId") String kakaoId,
                                             @PathVariable Long bagId,
                                             @PathVariable Long itemId) {
        itemService.deleteItem(kakaoId, bagId, itemId);
        return ResponseEntity.ok("삭제 되었습니다.");
    }

    // is_packed 토글 API
    @PatchMapping("/{itemId}/toggle-packed")
    public ResponseEntity<ItemResponseDto> togglePacked(@PathVariable("memberId") String kakaoId,
                                                        @PathVariable Long bagId,
                                                        @PathVariable Long itemId) {
        ItemResponseDto itemResponseDto = itemService.togglePacked(kakaoId, bagId, itemId);
        return ResponseEntity.ok(itemResponseDto);
    }
}
