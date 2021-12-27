package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/city")
@CrossOrigin(origins = "*")
public class CityController {

    @Autowired
    public CityService _cityService;

    @GetMapping("/")
    public ResponseEntity loadAllCity() throws Exception{
        ResultData resultData=new ResultData();
        try{
            List<City> cityData=_cityService.getAllCity();

            resultData.data=(ArrayList)cityData;
            resultData.total=cityData.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}