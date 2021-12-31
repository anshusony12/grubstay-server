package com.grubstay.server.services.impl;

import com.grubstay.server.entities.Location;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.repos.LocationRepository;
import com.grubstay.server.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> loadAllLocation(){
        List<Location> locations=new ArrayList<>();
        try{
            locations=this.locationRepository.findAll();
        }
        catch(Exception e){
            e.printStackTrace();;
            throw e;
        }
        return locations;
    }

    @Override
    public void addLocation(Location location) throws Exception {
        Location existingLocation=this.locationRepository.findLocationByLocationId(location.getLocationId());
        if(existingLocation!=null){
            throw new HelperException("Location Already Exist!");
        }
        else{
            this.locationRepository.save(location);
        }
    }

    @Override
    public Location updateCityByCityId(boolean status, Long locationId) throws Exception{
        Location location=null;
        try{
            location=this.locationRepository.findLocationByLocationId(locationId);
            if(location==null){
                throw new HelperException("Location not present!");
            }else{
                int i=this.locationRepository.updateLocationByLocationId(status,locationId);
                if(i > 0){
                    location=this.locationRepository.findLocationByLocationId(locationId);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return location;
    }
}
