package com.example.travelbag.domain.bag.service;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.bag.dto.BagResponseDto;
import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.bag.repository.BagRepository;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.repository.MemberRepository;
import com.example.travelbag.global.error.exception.CustomException;
import com.example.travelbag.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BagService {

    private final BagRepository bagRepository;
    private final MemberRepository memberRepository;

    // 가방 생성 API
    @Transactional
    public BagResponseDto createTemporaryBag(BagRequestDto bagRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        Bag bag = Bag.builder()
                    .name(bagRequestDto.getName())
                    .build();

        Bag save = bagRepository.save(bag);

        return BagResponseDto.builder()
                .id(save.getId())
                .name(save.getName())
                .isTemporary(true)
                .build();
    }

    // 가방 조회 API
    @Transactional(readOnly = true)
    public BagResponseDto getBag(Long bagId, Long memberId) {
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // 권한 체크: 자신의 가방만 조회 가능
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .isTemporary(false)
                .build();
    }

    // 가방 이름 수정 API
    @Transactional
    public BagResponseDto updateBagName(Long memberId, Long bagId, BagRequestDto bagRequestDto) {
        Bag bag = bagRepository.findById(bagId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAG_NOT_FOUND));

        // 권한 체크: 자신의 가방만 수정 가능
        if (!bag.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.NOT_BAG_OWNER);
        }

        bag.updateName(bagRequestDto);

        return BagResponseDto.builder()
                .id(bag.getId())
                .name(bag.getName())
                .isTemporary(false)
                .build();
    }
}

