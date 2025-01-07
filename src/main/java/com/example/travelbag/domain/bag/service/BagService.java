package com.example.travelbag.domain.bag.service;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.global.enums.Template;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class BagService {

    private final BagRepository bagRepository;
    private final MemberRepository memberRepository;

    // 가방 생성 API
    @Transactional
    public BagResponseDto createTemporaryBag(Long memberId, Template template, BagRequestDto bagRequestDto) {
        // memberId에 해당하는 Member 객체를 조회하고 없으면 예외 처리
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // Bag 객체 생성 시 Template을 함께 설정
        Bag bag = Bag.builder()
                .name(bagRequestDto.getName())
                .member(member) // Member 할당
                .template(template) // Template 설정
                .build();

        // Bag 객체 저장
        Bag savedBag = bagRepository.save(bag);

        // BagResponseDto 반환
        return BagResponseDto.builder()
                .id(savedBag.getId())
                .name(savedBag.getName())
                .isTemporary(true)
                .build();
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
