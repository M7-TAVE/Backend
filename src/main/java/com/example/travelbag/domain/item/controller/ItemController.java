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
    @PostMapping
    @Override
    public ResponseEntity<ItemResponseDto> createItem(@PathVariable Long memberId,
                                                      @PathVariable Long bagId,
                                                      @RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto item = itemService.createItem(memberId, bagId, itemRequestDto);
        return ResponseEntity.ok(item);
    }

    // 카테고리별 물품 조회 (챙길 것들)
    @GetMapping
    @Override
    public ResponseEntity<List<ItemResponseDto>> getItems(@PathVariable Long memberId,
                                                          @PathVariable Long bagId) {
        List<ItemResponseDto> items = itemService.getItems(memberId, bagId);
        return ResponseEntity.ok(items);
    }

    // 물품 이름 수정 (챙길 것들)
    @PatchMapping("/{itemId}/name")
    @Override
    public ResponseEntity<ItemResponseDto> updateItemName(@PathVariable Long memberId,
                                                          @PathVariable Long bagId,
                                                          @PathVariable Long itemId,
                                                          @RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto itemResponseDto = itemService.updateItemName(memberId, bagId, itemId, itemRequestDto);
        return ResponseEntity.ok(itemResponseDto);
    }

    // 물품 삭제 (챙길 것들)
    @DeleteMapping("/{itemId}")
    @Override
    public ResponseEntity<String> deleteItem(@PathVariable Long memberId,
                                             @PathVariable Long bagId,
                                             @PathVariable Long itemId) {
        itemService.deleteItem(memberId, bagId, itemId);
        return ResponseEntity.ok("삭제 되었습니다.");
    }
}
