package com.example.travelbag.domain.member.mapper;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import com.example.travelbag.domain.member.entity.Member;

public class MemberMapper {

    // Dto -> Entity
    public static Member toMemberEntity(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .name(memberRequestDto.getName())
                .build();
    }

    // Entity -> Dto
    public static MemberResponseDto toMemberDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}
