package com.example.travelbag.domain.member.controller;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import com.example.travelbag.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.travelbag.domain.member.controller.api.MemberApi;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @PostMapping("/api/member")
    @Override
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto responseDto = memberService.createMember(memberRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/api/member/{memberId}")
    @Override
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("memberId") Long memberId) {
        MemberResponseDto member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }
}
