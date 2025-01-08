package com.example.travelbag.domain.bag.repository;

import com.example.travelbag.domain.bag.entity.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BagRepository extends JpaRepository<Bag, Long> {

    List<Bag> findAllByMemberId(Long memberId);
}
