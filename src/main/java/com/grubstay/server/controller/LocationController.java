package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Location;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.CityService;
import com.grubstay.server.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService _locationService;

    @Autowired
    private CityService _cityService;

    @GetMapping("/")
    public ResponseEntity loadAllLocation(){
        ResultData resultData=new ResultData();
        try{
            List<Location> locations=this._locationService.loadAllLocation();

            ArrayList allLocations=new ArrayList();
            allLocations.add(locations);

            resultData.data=allLocations;
            resultData.total=allLocations.size();
            resultData.success="success";
        }
        catch(Exception e){
            resultData.error= e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>( resultData, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity addLocation(@ModelAttribute("location") Location location, @RequestParam("cityId") Integer cityId){
        ResultData resultData=new ResultData();
        try{
            City city=this._cityService.getCity(cityId);
            location.setCity(city);
            this._locationService.addLocation(location);
            resultData.success="saved";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity updateLocation(@ModelAttribute("location") Location location){
        ResultData resultData=new ResultData();
        boolean status=false;
        Long locationId=0l;
        try{
            locationId=location.getLocationId();
            if(locationId!=0) {
                Location updatedLocation = this._locationService.updateCityByCityId(status,locationId);
                if(updatedLocation!=null){
                    ArrayList updateLocation=new ArrayList();
                    updateLocation.add(location);
                    resultData.data=updateLocation;
                    resultData.success="updated";
                }
                else{
                    resultData.success="failed";
                }
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
