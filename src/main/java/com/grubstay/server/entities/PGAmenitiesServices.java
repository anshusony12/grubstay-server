package com.grubstay.server.entities;

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

    @OneToOne(fetch = FetchType.EAGER)
    private PayingGuest pgStayId;
}
