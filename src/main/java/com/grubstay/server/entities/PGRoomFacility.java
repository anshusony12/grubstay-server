package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PGRoomFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rmfId;

    private boolean attachedWashroom;
    private boolean bedWithMattress;
    private boolean ceilingFan;
    private boolean hotWatersupply;
    private boolean airCooler;
    private boolean tvDth;
    private boolean wardrobe;
    private boolean safetyLocker;

    // One Set of Room Facitity belongs to One Paying Guest Room
    @OneToOne(fetch = FetchType.EAGER)
    private PayingGuest pgStayId;
}
