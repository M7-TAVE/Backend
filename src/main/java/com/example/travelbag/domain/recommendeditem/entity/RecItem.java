package com.example.travelbag.domain.recommendeditem.entity;

import com.example.travelbag.global.enums.ItemCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecItem {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private ItemCategory category;
}
