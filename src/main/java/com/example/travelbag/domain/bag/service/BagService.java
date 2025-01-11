package com.example.travelbag.domain.bag.service;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.global.enums.Template;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BagService {

    private final BagRepository bagRepository;
    private final MemberRepository memberRepository;

    // 가방 생성 API
    @Transactional
    public BagResponseDto createTemporaryBag(Long memberId, Template template, BagRequestDto bagRequestDto) {
        // 멤버 조회 또는 예외 발생
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 새로운 가방 생성
        Bag bag = Bag.builder()
                .name(bagRequestDto.getName())
                .member(member)
                .template(template)
                .build();

        // 템플릿에서 아이템 생성
        if (template != null && template.getDefaultItems() != null) {
            List<Item> templateItems = createTemplateItems(template, bag);
            bag.getItems().addAll(templateItems);
        }

        // 가방과 아이템들 저장
        Bag savedBag = bagRepository.save(bag);

        // 아이템들을 DTO로 변환
        List<ItemResponseDto> itemDtos = savedBag.getItems().stream()
                .map(item -> ItemResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .category(item.getCategory())
                        .isPacked(false)  // 새로운 아이템은 기본적으로 체크되지 않은 상태
                        .build())
                .collect(Collectors.toList());

        // 모든 정보가 포함된 응답 반환
        return BagResponseDto.builder()
                .id(savedBag.getId())
                .name(savedBag.getName())
                .template(savedBag.getTemplate())
                .isTemporary(true)
                .build();
    }

    private List<Item> createTemplateItems(Template template, Bag bag) {
        List<Item> items = new ArrayList<>();

        template.getDefaultItems().forEach((category, itemNames) -> {
            itemNames.forEach(itemName -> {
                Item item = Item.builder()
                        .name(itemName)
                        .category(category)
                        .bag(bag)
                        .isPacked(false)  // 새로운 아이템은 체크되지 않은 상태로 시작
                        .build();
                items.add(item);
            });
        });

        return items;
    }

    // 가방 전체 조회 API
    @Transactional(readOnly = true)
    public List<BagResponseDto> getBags(Long memberId) {
        // memberId에 해당하는 Member 객체를 조회하고 없으면 예외 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // memberId에 해당하는 모든 Bag 객체 조회
        List<Bag> bags = bagRepository.findAllByMemberId(memberId);

        // 조회된 Bag 객체를 BagResponseDto로 변환하여 반환
        return bags.stream()
                .map(bag -> BagResponseDto.builder()
                        .id(bag.getId())
                        .name(bag.getName())
                        .template(bag.getTemplate())
                        .isTemporary(false)
                        .build())
                .collect(Collectors.toList());
    }

    // 가방 조회 API
    @Transactional(readOnly = true)
    public BagResponseDto getBag(Long bagId, Long memberId) {
        // bagId에 해당하는 Bag 객체를 조회하고 없으면 예외 처리
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // Bag 객체가 지정된 memberId와 일치하는지 확인
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        // BagResponseDto 반환
        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .isTemporary(false)
                .build();
    }

    // 가방 이름 수정 API
    @Transactional
    public BagResponseDto updateBagName(Long memberId, Long bagId, BagRequestDto bagRequestDto) {
        // bagId에 해당하는 Bag 객체를 조회하고 없으면 예외 처리
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // Bag 객체가 지정된 memberId와 일치하는지 확인
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        // Bag 객체의 이름 업데이트
        bag.updateName(bagRequestDto);

        // 업데이트된 Bag 객체를 BagResponseDto로 변환하여 반환
        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .isTemporary(false)
                .build();
    }

    // 가방 삭제 API
    @Transactional
    public void deleteBag(Long memberId, Long bagId) {
        // bagId에 해당하는 Bag 객체를 조회하고 없으면 예외 처리
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // Bag 객체가 지정된 memberId와 일치하는지 확인
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        // Bag 객체 삭제
        bagRepository.delete(bag);
    }
}
