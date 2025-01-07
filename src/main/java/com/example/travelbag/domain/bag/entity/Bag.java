package com.example.travelbag.domain.bag.entity;

import com.example.travelbag.domain.bag.dto.BagRequestDto;
import com.example.travelbag.domain.common.model.BaseTimeEntity;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.domain.member.entity.Member;
import com.example.travelbag.global.enums.Template;
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
public class Bag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Template template;

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public void updateName(BagRequestDto bagRequestDto) {
        if (bagRequestDto.getName() != null) {
            this.name = bagRequestDto.getName();
        }
    }
}
