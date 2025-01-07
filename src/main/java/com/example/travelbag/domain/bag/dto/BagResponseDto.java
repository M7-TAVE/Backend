package com.example.travelbag.domain.bag.dto;

import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.global.enums.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BagResponseDto {

    private Long id;

    private String name;

    private Template template;

    private List<ItemResponseDto> items;

    private boolean isTemporary;
    // isTemporary가 true이면 이름 입력란에 포커스를 자동으로 맞출 수 있음
    // 임시 상태일 때는 다른 동작을 제한할 수 있음
}
