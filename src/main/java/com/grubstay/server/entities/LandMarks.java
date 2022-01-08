package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LandMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int landMarkId;

    private String landMarkName;

    private String landMarkImageName;

    private String landMarkImage;

    //Many Landmarks can be available in nearby location to Single Paying Guest
    @ManyToOne(fetch = FetchType.EAGER)
    private PayingGuest pgStayId;
}
