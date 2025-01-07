package com.example.travelbag.domain.item.repository;

import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.global.enums.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByBagIdAndNameAndCategory(Long bagId, String name, ItemCategory category);

}
