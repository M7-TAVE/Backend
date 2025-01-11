package com.example.travelbag.global.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public enum Template {
    FREESTYLE(1L, "내 마음대로 시작하기", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "신분증", "유심", "체크카드", "지갑", "현지화폐", "e-티켓(항공권)", "숙소바우처"),
            ItemCategory.MEDICAL, List.of("마스크", "종합감기약", "소화제", "진통제", "반창고", "연고", "두통약/멀미약", "영양제"),
            ItemCategory.CLOTHING, List.of("팬티", "양말", "모자", "신발"),
            ItemCategory.TOILETRIES, List.of("칫솔", "치약", "바디워시", "샴푸", "컨디셔너"),
            ItemCategory.ELECTRONICS, List.of("휴대폰", "휴대폰 충전기", "보조배터리", "이어폰"),
            ItemCategory.OTHER, List.of("텀블러", "우산", "휴대용 휴지", "물티슈", "목베개", "수건")
    )),

    WOMAN_SOLO(2L, "여자 혼자 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "신분증", "유심", "체크카드", "지갑", "현지화폐", "여행자 보험", "e-티켓(항공권)", "주요 서류", "작은 자물쇠"),
            ItemCategory.MEDICAL, List.of("마스크", "종합감기약", "소화제", "진통제", "반창고"),
            ItemCategory.CLOTHING, List.of("팬티", "브라", "잠옷", "양말", "가방", "긴팔", "긴바지", "후드집업"),
            ItemCategory.TOILETRIES, List.of("칫솔", "폼클렌징", "클렌징 오일", "로션", "세안밴드", "바디워시", "샴푸", "스킨", "자외선 차단제", "아이라이너", "아이브로우", "쉐딩", "하이라이터", "블러셔", "파운데이션", "비비크림", "쿠션", "쉐도우", "마스카라", "립밤"),
            ItemCategory.ELECTRONICS, List.of("휴대폰", "휴대폰 충전기", "보조배터리", "휴대용 고데기", "이어폰", "멀티어댑터"),
            ItemCategory.OTHER, List.of("향수", "머리끈", "호신용품")
    )),

    MAN_SOLO(3L, "남자 혼자 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "신분증", "유심", "체크카드", "지갑", "현지화폐", "e-티켓(항공권)"),
            ItemCategory.MEDICAL, List.of("마스크", "진통제", "반창고"),
            ItemCategory.CLOTHING, List.of("팬티", "양말", "후드집업"),
            ItemCategory.TOILETRIES, List.of("칫솔", "치약", "면도기"),
            ItemCategory.ELECTRONICS, List.of("휴대폰", "휴대폰 충전기", "보조배터리", "이어폰"),
            ItemCategory.OTHER, List.of("텀블러", "휴대용 휴지", "물티슈")
    )),

    BUSINESS(4L, "비즈니스 여행", Map.of(
            ItemCategory.ESSENTIAL, List.of("여권", "여권사본", "신분증", "유심", "신용카드", "지갑", "현지화폐", "여행자 보험", "e-티켓(항공권)", "주요 서류", "국제면허증"),
            ItemCategory.MEDICAL, List.of("마스크", "진통제"),
            ItemCategory.CLOTHING, List.of("정장", "구두", "넥타이", "팬티", "양말"),
            ItemCategory.TOILETRIES, List.of("칫솔", "치약", "면도기"),
            ItemCategory.ELECTRONICS, List.of("노트북", "노트북 충전기", "휴대폰", "휴대폰 충전기", "보조배터리", "이어폰"),
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

