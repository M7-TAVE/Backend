package com.example.travelbag.domain.member.service;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.domain.member.mapper.MemberMapper;
import com.example.travelbag.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {

        if (memberRepository.findByKakaoId(memberRequestDto.getKakaoId()).isPresent()) {
            throw new IllegalArgumentException("already registerd user !!!");
        }

        Member memberEntity = MemberMapper.toMemberEntity(memberRequestDto);
        memberRepository.save(memberEntity);
        return MemberMapper.toMemberDto(memberEntity);
    }
}
