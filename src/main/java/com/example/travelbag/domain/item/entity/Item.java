package com.example.travelbag.domain.item.entity;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.item.dto.ItemRequestDto;
import com.example.travelbag.domain.item.dto.ItemResponseDto;
import com.example.travelbag.global.enums.ItemCategory;
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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    private boolean isPacked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bag_id")
    private Bag bag;

    public void updateItemInfo(ItemRequestDto itemRequestDto) {
        if (itemRequestDto.getName() != null) {
            this.name = itemRequestDto.getName();
        }
    }
}
