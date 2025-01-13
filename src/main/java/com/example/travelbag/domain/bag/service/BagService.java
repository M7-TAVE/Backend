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
import org.springframework.http.ResponseEntity;
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
    public BagResponseDto createTemporaryBag(Long memberId, Long templateId, BagRequestDto bagRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Template template = Template.fromId(templateId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));

        Bag bag = Bag.builder()
                .name(bagRequestDto.getName())
                .member(member)
                .template(template)
                .build();

        if (template.getDefaultItems() != null) {
            List<Item> templateItems = createTemplateItems(template, bag);
            bag.getItems().addAll(templateItems);
        }

        Bag savedBag = bagRepository.save(bag);

        List<ItemResponseDto> itemDtos = savedBag.getItems().stream()
                .map(item -> ItemResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .category(item.getCategory())
                        .isPacked(item.isPacked())  // 새로운 아이템은 기본적으로 체크되지 않은 상태
                        .build())
                .collect(Collectors.toList());

        return BagResponseDto.builder()
                .id(savedBag.getId())
                .name(savedBag.getName())
                .template(savedBag.getTemplate())
                .isTemporary(savedBag.isTemporary())
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
                        .isPacked(false)
                        .build();
                items.add(item);
            });
        });

        return items;
    }

    // 가방 전체 조회 API
    @Transactional(readOnly = true)
    public List<BagResponseDto> getBags(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<Bag> bags = bagRepository.findAllByMemberId(memberId);

        return bags.stream()
                .map(bag -> BagResponseDto.builder()
                        .id(bag.getId())
                        .name(bag.getName())
                        .template(bag.getTemplate())
                        .isTemporary(bag.isTemporary())
                        .build())
                .collect(Collectors.toList());
    }

    // 가방 조회 API
    @Transactional(readOnly = true)
    public BagResponseDto getBag(Long bagId, Long memberId) {
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .template(bag.getTemplate())
                .isTemporary(bag.isTemporary())
                .build();
    }

    // 가방 이름 수정 API
    @Transactional
    public BagResponseDto updateBagName(Long memberId, Long bagId, BagRequestDto bagRequestDto) {
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        bag.updateName(bagRequestDto);

        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .template(bag.getTemplate())
                .isTemporary(bag.isTemporary())
                .build();
    }

    // 가방 삭제 API
    @Transactional
    public void deleteBag(Long memberId, Long bagId) {
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        bagRepository.delete(bag);
    }

    // is_temporary 토글 API
    @Transactional
    public BagResponseDto toggleTemporary(Long memberId, Long bagId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        if (!bag.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        bag.toggleTemporary();

        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .template(bag.getTemplate())
                .isTemporary(bag.isTemporary())
                .build();
    }
}
