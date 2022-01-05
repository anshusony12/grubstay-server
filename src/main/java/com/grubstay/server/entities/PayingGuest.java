package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grubstay.server.helper.PGIdGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PayingGuest {

    @Id
    @GenericGenerator(name = "pg_id", strategy = "com.grubstay.server.helper.PGIdGenerator")
    @GeneratedValue(generator = "pg_id")
    @Column(name="pg_id")
    private String pgId;

    private String pgName;
    private String pgDesc;
    private String pgAddress;
    private String pgGender;
    private boolean weekly;
    private boolean monthly;
    private boolean daily;

    private int singleMemPgPrc;
    private int doubleMemPgPrc;
    private int tripleMemPgPrc;

    private boolean status;



    private double distFromSubLoc;

    //Many PayingGuest can be available in single location
    @ManyToOne(fetch = FetchType.EAGER)
    private SubLocation subLocation;

    //One PayingGuest Room can have a single set of Amenties and Services
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    private PGAmenitiesServices amenitiesServices;

    //One Paying Guest Room can ave a single set of Room Facitliyt
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    private PGRoomFacility roomFacility;

    //Many LandMarks can be available nearer to one PayingGuest
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pgStayId")
    @JsonIgnore
    private List<LandMarks> landMarksList=new ArrayList<>();

}

