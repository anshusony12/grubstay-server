package com.grubstay.server.entities.compository;

import java.io.Serializable;

public class CityId implements Serializable {
    private Integer cityId;
    private String cityName="Banglore";

    public CityId(Integer cityId, String cityName){
        this.cityId=cityId;
        this.cityName=cityName;
    }
    public CityId(){}
}
