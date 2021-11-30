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

    @ManyToOne(fetch = FetchType.EAGER)
    private PayingGuest pgStayId;
}
