package com.example.travelbag.domain.member.controller;

import com.example.travelbag.domain.member.dto.MemberRequestDto;
import com.example.travelbag.domain.member.dto.MemberResponseDto;
import com.example.travelbag.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.travelbag.domain.member.controller.api.MemberApi;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @PostMapping("/api/member")
    @Override
    public ResponseEntity<String> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        MemberResponseDto responseDto = memberService.createMember(memberRequestDto);
        return ResponseEntity.ok(responseDto.getName() + "님이 회원가입 되었습니다.");
    }
      
    @GetMapping("/test")
    public String test() {
        return "Test Successful!";
    }
}
