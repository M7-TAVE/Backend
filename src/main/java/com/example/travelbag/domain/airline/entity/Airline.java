package com.example.travelbag.domain.airline.entity;

import com.example.travelbag.domain.location_airline.entity.LocationAirline;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String image_url;

    private String url;

    @OneToMany(mappedBy = "airline")
    private List<LocationAirline> location_airlines = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<LocationAirline> getLocation_airlines() {
        return location_airlines;
    }

    public void setLocation_airlines(List<LocationAirline> location_airlines) {
        this.location_airlines = location_airlines;
    }

}
