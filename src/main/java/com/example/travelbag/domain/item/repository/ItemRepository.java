package com.example.travelbag.domain.item.repository;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.global.enums.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByBagIdAndNameAndCategory(Long bagId, String name, ItemCategory itemCategory);
    List<Item> findByBagAndCategory(Bag bag, ItemCategory category);

}
