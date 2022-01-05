package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private boolean tvDth;
    private boolean wardrobe;
    private boolean safetyLocker;
    private boolean tableChair;

    // One Set of Room Facitity belongs to One Paying Guest Room
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PayingGuest pgStayId;
}
