package com.grubstay.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PayingGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pgId;

    private String pgName;
    private String pgDesc;
    private String pgAddress;
    private String pgType;
    private boolean pgForMale;
    private boolean pgForFemale;
    private boolean pgForBoth;

    private String pgImage;

    private int singleMemPgPrc;
    private int doubleMemPgPrc;
    private int tripleMemPgPrc;

    private double distFromSubLoc;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubLocation subLocation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    private PGAmenitiesServices amenitiesServices;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    private PGRoomFacility roomFacility;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    private List<LandMarks> landMarksList=new ArrayList<>();
}

