package com.example.travelbag.domain.member.mapper;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import com.example.travelbag.domain.member.entity.Member;
public class MemberMapper {

    // Dto -> Entity
    public static Member toMemberEntity(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .kakaoId(memberRequestDto.getKakaoId())
                .email(memberRequestDto.getEmail())
                .nickname(memberRequestDto.getNickname())
                .build();
    }

    // Entity -> Dto
    public static MemberResponseDto toMemberDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .kakaoId(member.getKakaoId()) // Member 객체 사용
                .email(member.getEmail())    // Member 객체 사용
                .nickname(member.getNickname()) // Member 객체 사용
                .build();
    }
}