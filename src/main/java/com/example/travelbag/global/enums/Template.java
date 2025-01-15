package com.example.travelbag.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public enum Template {
    FREESTYLE(1L, "내 마음대로 시작하기", Map.of(
            ItemCategory.ESSENTIAL, List.of(),
            ItemCategory.MEDICAL, List.of(),
            ItemCategory.CLOTHING, List.of(),
            ItemCategory.TOILETRIES, List.of(),
            ItemCategory.ELECTRONICS, List.of(),
            ItemCategory.OTHER, List.of()
    )),

    WOMAN_SOLO(2L, "여자 혼자 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "유심", "여행자 보험", "체크카드", "지갑", "현지화폐", "주요 서류", "e-티켓(항공권)", "신분증", "작은 자물쇠"),
            ItemCategory.MEDICAL, List.of("종합감기약", "소화제"),
            ItemCategory.CLOTHING, List.of("팬티", "잠옷", "양말", "가방", "긴팔", "긴바지"),
            ItemCategory.TOILETRIES, List.of("폼클렌징", "로션", "세안밴드", "칫솔", "스킨", "바디워시", "샴푸", "여성 위생용품", "자외선 차단제", "립밤"),
            ItemCategory.ELECTRONICS, List.of("휴대폰 충전기", "휴대용 고데기", "보조배터리", "이어폰", "멀티어댑터", "휴대폰"),
            ItemCategory.OTHER, List.of("핸드폰 목걸이케이스", "머리끈", "호신용품")
    )),

    MAN_SOLO(3L, "남자 혼자 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "신분증", "유심", "체크카드", "지갑", "현지화폐", "e-티켓(항공권)"),
            ItemCategory.MEDICAL, List.of(),
            ItemCategory.CLOTHING, List.of("팬티", "양말"),
            ItemCategory.TOILETRIES, List.of("면도기", "칫솔"),
            ItemCategory.ELECTRONICS, List.of("휴대폰", "휴대폰 충전기", "보조배터리", "이어폰"),
            ItemCategory.OTHER, List.of()
    )),

    BUSINESS(4L, "비즈니스 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "유심", "여행자 보험", "신용카드", "현지화페", "주요 서류", "e-티켓(항공권)", "신분증", "국제면허증", "여권사본"),
            ItemCategory.MEDICAL, List.of(),
            ItemCategory.CLOTHING, List.of("팬티", "양말"),
            ItemCategory.TOILETRIES, List.of("칫솔"),
            ItemCategory.ELECTRONICS, List.of("노트북", "노트북 충전기", "휴대폰 충전기", "보조배터리", "이어폰", "휴대폰"),
            ItemCategory.OTHER, List.of()
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

