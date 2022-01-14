package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cityId;

    @Column(unique = true)
    private String cityName;

    private String cityImageName;

    private String cityImage;

    private boolean status;

    private int totalStays;
    // One City can have many locations
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
    @JsonIgnore
    private List<Location> locationList=new ArrayList<>();
}
