package com.example.travelbag.domain.attraction.repository;

import com.example.travelbag.domain.attraction.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long>  {
    // 여행지별 관광지 조회
    List<Attraction> findByLocationId(Long location_id);
}
