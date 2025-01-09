package com.example.travelbag.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public enum Template {
    FREESTYLE(1L, "내 마음대로 시작하기", Map.of(
            ItemCategory.CLOTHING, List.of("겉옷", "양말", "목도리"),
            ItemCategory.TOILETRIES, List.of("칫솔", "치약", "샴푸"),
            ItemCategory.ELECTRONICS, List.of("에어팟", "휴대폰", "노트북")
    )),
    WOMAN_SOLO(2L,"여자 혼자 여행", Map.of(
            ItemCategory.CLOTHING, List.of("겉옷", "양말", "목도리"),
            ItemCategory.TOILETRIES, List.of("화장품", "드라이기", "세면도구"),
            ItemCategory.ELECTRONICS, List.of("보조배터리", "카메라", "노트북")
    )),
    MAN_SOLO(3L,"남자 혼자 여행", Map.of(
            ItemCategory.CLOTHING, List.of("운동화", "양말", "겉옷"),
            ItemCategory.TOILETRIES, List.of("면도기", "치약", "샴푸"),
            ItemCategory.ELECTRONICS, List.of("노트북", "보조배터리", "에어팟")
    )),
    BUSINESS(4L,"비즈니스 여행", Map.of(
            ItemCategory.CLOTHING, List.of("정장", "구두", "넥타이"),
            ItemCategory.ELECTRONICS, List.of("노트북", "보조배터리", "충전기"),
            ItemCategory.OTHER, List.of("명함", "서류가방", "필기구")
    ));

    private final Long id;
    private final String title;
    private final Map<ItemCategory, List<String>> defaultItems;

    Template(Long id, String title, Map<ItemCategory, List<String>> defaultItems) {
        this.id = id;
        this.title = title;
        this.defaultItems = defaultItems;
    }

    public static Optional<Template> fromId(Long id) {
        return Arrays.stream(values())
                .filter(template -> template.id.equals(id))
                .findFirst();
    }
}
