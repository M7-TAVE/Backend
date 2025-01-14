package com.example.travelbag.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ItemCategory {
    ESSENTIAL(1L, "필수"),
    MEDICAL(2L, "의료"),
    CLOTHING(3L, "의류"),
    TOILETRIES(4L, "세면도구"),
    ELECTRONICS(5L, "전자기기"),
    OTHER(6L, "기타");

    private final Long id;
    private final String title;

    ItemCategory(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Optional<ItemCategory> fromId(Long id) {
        return Arrays.stream(values())
                .filter(category -> category.id.equals(id))
                .findFirst();
    }
}
