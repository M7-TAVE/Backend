package com.example.travelbag.domain.recommendeditem.repository;

import com.example.travelbag.domain.recommendeditem.entity.RecItem;
import com.example.travelbag.global.enums.ItemCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RecItemRepository extends JpaRepository<RecItem, Long> {

    List<RecItem> findAllByCategory(ItemCategory category);

    @Query("SELECT DISTINCT r FROM RecItem r WHERE r.category = :category")
    List<RecItem> findDistinctByCategory(ItemCategory category);
}
