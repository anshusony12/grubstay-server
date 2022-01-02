package com.grubstay.server.controller;

import com.grubstay.server.entities.Location;
import com.grubstay.server.entities.SubLocation;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.CityService;
import com.grubstay.server.services.LocationService;
import com.grubstay.server.services.SubLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sublocation")
@CrossOrigin(origins = "*")
public class SubLocationController {

    @Autowired
    private SubLocationService _subLocationService;

    @Autowired
    private LocationService _locationService;

    @GetMapping("/")
    public ResponseEntity loadAllSubLocation(){
        ResultData resultData=new ResultData();
        try{
            List<SubLocation> subLocationList=this._subLocationService.loadAllSubLocation();
            if(subLocationList.size() > 0){
                ArrayList sublocationList=new ArrayList();
                sublocationList.add(subLocationList);

                resultData.data=sublocationList;
                resultData.success="success";
            }
            else{
                throw new HelperException("No Record Found");
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity addSubLocation(@ModelAttribute("sublocation") SubLocation sublocation, @RequestParam("locationId") Integer locationId, @RequestParam("cityId") Integer cityId){
        ResultData resultData=new ResultData();
        Long locationID=0l;
        try{
            if(locationId!=0){
                locationID=Long.parseLong(locationId.toString());
                Location location=_locationService.findLocationByLocationId(locationID);
                SubLocation subLocation=null;
                if(location!=null) {
                    location = this._locationService.locationUsingCityAndLocationName(location.getLocationName(),cityId);
                    subLocation=this._subLocationService.subLocaionUsingNameAndLocationId(sublocation.getSubLocationName(),location.getLocationId());
                }
                if(location!=null && subLocation==null){
                    subLocation=sublocation;
                    subLocation.setLocation(location);
                    SubLocation subLoc=this._subLocationService.createSubLocation(subLocation);
                    if(subLoc!=null){
                        resultData.success="success";
                    }
                    else{
                        resultData.success="failed";
                    }
                }
                else{
                    throw new HelperException("Location not found!");
                }
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity updateSubLocation(@ModelAttribute("subLocation") SubLocation subLocation){
        ResultData resultData=new ResultData();
        try{
            SubLocation subLocation1=subLocation;
            if(subLocation!=null) {
                boolean updateStatus = this._subLocationService.updateSubLocation(subLocation.getSubLocationName(), subLocation.isStatus(), subLocation.getSubLocationId());
                resultData.success="update";
            }else{
                resultData.success="failed";
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/{sublocationId}")
    public ResponseEntity deleteSubLocation(@PathVariable("sublocationId") Integer subLocationId){
        ResultData resultData=new ResultData();
        try{
            this._subLocationService.deleteSubLocation(Long.parseLong(subLocationId.toString()));
            resultData.success = "deleted";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
