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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String currency_unit;

    @OneToMany(mappedBy = "location")
    private List<LocationAirline> location_airlines = new ArrayList<>();

    public List<Airline> getAirlines() {
        return location_airlines.stream()
                .map(LocationAirline::getAirline)
                .collect(Collectors.toList());
    }
}
