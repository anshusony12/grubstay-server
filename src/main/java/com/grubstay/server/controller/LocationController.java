package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Location;
import com.grubstay.server.entities.SubLocation;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.SubLocationRepository;
import com.grubstay.server.services.CityService;
import com.grubstay.server.services.LocationService;
import com.grubstay.server.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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

    @Autowired
    private StorageService _storageService;

    @Autowired
    private SubLocationRepository subLocationRepository;

    @GetMapping("/")
    public ResponseEntity loadAllLocation(){
        ResultData resultData=new ResultData();
        try{
            List<Location> locations=this._locationService.loadAllLocation();
            for(Location l: locations){
                City city=l.getCity();
                File file=new File(this._storageService.getCityRootPath(), city.getCityImageName());
                if(file.exists()) {
                    String imageSrc = this._storageService.getImageSrc(file);
                    city.setCityImage(imageSrc);
                }else{
                    File defaultImage = new File(this._storageService.getWorkingPath()+"static/image/","defaultImage.jpeg");
                    if(defaultImage.exists()){
                        String imageSrc=this._storageService.getImageSrc(defaultImage);
                        if(imageSrc!=null){
                            city.setCityImage(imageSrc);
                        }
                    }
                }
                l.setCity(city);
            }
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
            String locationName=location.getLocationName();
            Integer cityID=location.getCity().getCityId();
            if(cityID!=0 && locationName!=""){
                Location existingLocation=this._locationService.locationUsingCityAndLocationName(locationName, cityID);
                if(existingLocation==null){
                    this._locationService.addLocation(location);
                    resultData.success="saved";
                }
                else{
                    resultData.success="failed";
                }
            }
            else{
                throw new HelperException("Something went wrong!");
            }
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
            status=location.isStatus();
            if(locationId!=0) {
                Location updatedLocation = this._locationService.updateLocationByLocationId(status,locationId);
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

    @DeleteMapping("/{locationId}")
    public ResponseEntity deleteLocation(@PathVariable("locationId") int locationId){
        ResultData resutlData=new ResultData();
        long locationID=0;
        try{
            if(locationId!=0) {
                locationID=locationId;
                boolean deleteStatus = this._locationService.deleteLocation(locationID);
                if(deleteStatus){
                    resutlData.success="Deleted";
                }
                else{
                    resutlData.success="Failed";
                }
            }else{
                throw new HelperException("Location Id not found!");
            }
        }
        catch(Exception e){
            resutlData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resutlData, HttpStatus.OK);
    }

    @GetMapping("/subLocation/{locationId}")
    public ResponseEntity getSubLocationsByLocationId(@PathVariable("locationId") Long locationId){
        ResultData resultData=new ResultData();
        try{
            List<SubLocation> subLocations = this.subLocationRepository.getSubLocationsByLocationId(locationId);
            resultData.data=(ArrayList) subLocations;
            resultData.total=subLocations.size();
            resultData.success="success";
        }
        catch(Exception e){
            resultData.error= e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>( resultData, HttpStatus.OK);
    }
}
