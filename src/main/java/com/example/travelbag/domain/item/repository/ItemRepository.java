package com.example.travelbag.domain.item.repository;

import com.example.travelbag.domain.bag.entity.Bag;
import com.example.travelbag.domain.item.entity.Item;
import com.example.travelbag.global.enums.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.bag.id = :bagId AND i.category = :category")
    List<Item> findByBagIdAndCategoryId(@Param("bagId") Long bagId, @Param("category") ItemCategory category);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Item i WHERE i.bag.id = :bagId AND i.name = :name AND i.category = :category")
    boolean existsByBagIdAndNameAndCategory(@Param("bagId") Long bagId,
                                            @Param("name") String name,
                                            @Param("category") ItemCategory category);

    List<Item> findAllByBagId(Long bagId);
}