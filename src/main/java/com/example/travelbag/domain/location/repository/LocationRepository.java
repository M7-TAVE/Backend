package com.example.travelbag.domain.location.repository;

import com.example.travelbag.domain.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    // 여행지 목록 조회 (이름 기준 정렬)
    List<Location> findAllByOrderByNameAsc();
}
