package com.grubstay.server.services;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Location;
import com.grubstay.server.repos.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LocationService{
    List<Location> loadAllLocation();
    void addLocation(Location location) throws Exception;
    Location updateLocationByLocationId(boolean status, Long locationId) throws Exception;
    boolean deleteLocation(Long locationId) throws Exception;
    boolean locationUsingCityAndLocationName(String locationName, Integer cityId) throws Exception;
}
