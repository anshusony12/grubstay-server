package com.grubstay.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

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

    public LandMarks() {
    }

    public LandMarks(int landMarkId, String landMarkName, String landMarkImageName, String landMarkImage, PayingGuest pgStayId) {
        this.landMarkId = landMarkId;
        this.landMarkName = landMarkName;
        this.landMarkImageName = landMarkImageName;
        this.landMarkImage = landMarkImage;
        this.pgStayId = pgStayId;
    }

    public int getLandMarkId() {
        return landMarkId;
    }

    public void setLandMarkId(int landMarkId) {
        this.landMarkId = landMarkId;
    }

    public String getLandMarkName() {
        return landMarkName;
    }

    public void setLandMarkName(String landMarkName) {
        this.landMarkName = landMarkName;
    }

    public String getLandMarkImageName() {
        return landMarkImageName;
    }

    public void setLandMarkImageName(String landMarkImageName) {
        this.landMarkImageName = landMarkImageName;
    }

    public String getLandMarkImage() {
        return landMarkImage;
    }

    public void setLandMarkImage(String landMarkImage) {
        this.landMarkImage = landMarkImage;
    }

    public void setPgStayId(PayingGuest pgStayId) {
        this.pgStayId = pgStayId;
    }

    public PayingGuest getPgStayId() {
        return pgStayId;
    }
}
