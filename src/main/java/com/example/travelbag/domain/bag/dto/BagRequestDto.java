package com.example.travelbag.domain.bag.dto;

import com.example.travelbag.global.enums.Template;
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

    private Template template;  // Template enum 추가
}
