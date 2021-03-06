package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private boolean status;

    // Many Location can be available in one city
    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    // One location can have multiple sub-locations
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "location")
    @JsonIgnore
    private List<SubLocation> subLocationList=new ArrayList<>();
}
