package com.example.travelbag.domain.restaurant.repository;

import com.example.travelbag.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>  {
    // 여행지별 관광지 조회
    List<Restaurant> findByLocationId(Long locationId);
}
