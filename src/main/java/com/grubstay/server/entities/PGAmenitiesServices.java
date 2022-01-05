package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PGAmenitiesServices {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int amsId;

    private boolean securitySurvialance;
    private boolean wifi;
    private boolean diningArea;
    private boolean meals;
    private boolean powerBackUp;
    private boolean tv;
    private boolean lift;
    private boolean washingMachine;
    private boolean parkingArea;
    private boolean waterFilter;

    // One set of Amentities and Service is belongs to one Paying Guest
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PayingGuest pgStayId;
}
