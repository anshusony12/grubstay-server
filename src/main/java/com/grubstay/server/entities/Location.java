package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long locationId;

    private String locationName;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "location")
    private List<SubLocation> subLocationList=new ArrayList<>();
}