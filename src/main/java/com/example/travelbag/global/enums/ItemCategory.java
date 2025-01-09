package com.example.travelbag.global.enums;

public enum ItemCategory {
    ESSENTIAL("필수품"),
    CLOTHING("의류"),
    TOILETRIES("위생용품"),
    ELECTRONICS("전자기기"),
    MEDICAL("의료품"),
    OTHER("기타");

    private final String description;

    ItemCategory(String description) {
        this.description = description;
    }
}
