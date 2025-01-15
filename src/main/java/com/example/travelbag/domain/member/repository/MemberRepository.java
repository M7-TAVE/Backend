package com.example.travelbag.domain.member.repository;

import com.example.travelbag.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.travelbag.domain.member.repository.MemberRepository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(String kakaoId);
}
