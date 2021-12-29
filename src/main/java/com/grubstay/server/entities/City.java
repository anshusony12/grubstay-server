package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubstay.server.entities.compository.CityId;
import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@IdClass(CityId.class)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cityId;

    @Id
    @Column(unique = true)
    private String cityName;

    private String cityImage;

    private boolean status;
    // One City can have many locations
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
    @JsonIgnore
    private List<Location> locationList=new ArrayList<>();
}
