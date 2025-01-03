package com.example.travelbag.domain.bag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BagRequestDto {
    private String name;

    private Long memberId;

    private String template; // 선택한 템플릿 정보
}
