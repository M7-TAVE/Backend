package com.example.travelbag.domain.bag.entity;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.common.model.BaseTimeEntity;
import com.example.travelbag.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateName(BagRequestDto bagRequestDto) {
        if (bagRequestDto.getName() != null) {
            this.name = bagRequestDto.getName();
        }
    }
}
