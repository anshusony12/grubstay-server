package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.CityRepository;
import com.grubstay.server.repos.LocationRepository;
import com.grubstay.server.repos.PGRepository;
import com.grubstay.server.repos.SubLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminHomeController {

    @Autowired
    private PGRepository pgRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SubLocationRepository subLocationRepository;

    @GetMapping("/")
    public ResponseEntity loadAllCounts() throws Exception{
        ResultData resultData=new ResultData();
        try {
            Map<String,Integer> totalCounts = null;
            int citiesCount = this.cityRepository.getCitiesCount();
            int locationsCount = this.locationRepository.getLocationsCount();
            int subLocationsCount = this.subLocationRepository.getSubLocationsCount();
            int pgCounts = this.pgRepository.getPgCounts();
            int pgMaleCounts = this.pgRepository.getPgMaleCounts();
            int pgFemaleCounts = this.pgRepository.getPgFemaleCounts();
            totalCounts = new HashMap<String,Integer>();
            totalCounts.put("citiesCount",citiesCount);
            totalCounts.put("locationsCount",locationsCount);
            totalCounts.put("subLocationsCount",subLocationsCount);
            totalCounts.put("pgCounts",pgCounts);
            totalCounts.put("pgMaleCounts",pgMaleCounts);
            totalCounts.put("pgFemaleCounts",pgFemaleCounts);

            ArrayList<Map<String,Integer>> list = new ArrayList<>();
            list.add(0,totalCounts);
            resultData.data = list;
            resultData.total = totalCounts.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
