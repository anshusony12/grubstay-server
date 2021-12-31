package com.grubstay.server.services;

import com.grubstay.server.entities.Location;
import com.grubstay.server.repos.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LocationService{
    List<Location> loadAllLocation();
    void addLocation(Location location) throws Exception;
    Location updateCityByCityId(boolean status, Long locationId) throws Exception;
}
