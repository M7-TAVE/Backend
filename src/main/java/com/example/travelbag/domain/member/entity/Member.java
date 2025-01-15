package com.example.travelbag.domain.member.entity;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String kakaoId; // 카카오 고유 ID (Unique)

    @Column(nullable = true)
    private String email; // 이메일

    @Column(nullable = true)
    private String nickname; // 닉네임

    @Column(nullable = true) // name 필드 추가
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bag> bags = new ArrayList<>();
}
