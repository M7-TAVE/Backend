package com.example.travelbag.domain.Souvenir.repository;

import com.example.travelbag.domain.Souvenir.entity.Souvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SouvenirRepository extends JpaRepository<Souvenir, Long>  {
    // 여행지별 관광지 조회
    List<Souvenir> findByLocationId(Long locationId);
}
