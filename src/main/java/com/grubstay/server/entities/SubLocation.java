package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SubLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long subLocationId;

    private String subLocationName;

    // Many Sub-Location can be belongs to One Location
    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    // One Location can have multiple PayingGuest
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "subLocation")
    private List<PayingGuest> payingGuestList=new ArrayList<>();

}
