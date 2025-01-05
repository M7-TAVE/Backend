package com.example.travelbag.domain.location.entity;

import com.example.travelbag.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "location")
    private List<LocationAirline> location_airlines = new ArrayList<>();

    public List<Airline> getAirlines() {
        return location_airlines.stream()
                .map(LocationAirline::getAirline)
                .collect(Collectors.toList());
    }
}
