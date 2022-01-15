package com.grubstay.server.controller;

import com.grubstay.server.entities.City;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.CityRepository;
import com.grubstay.server.services.CityService;
import com.grubstay.server.services.LocationService;
import com.grubstay.server.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/city")
@CrossOrigin(origins = "*")
public class CityController {

    @Autowired
    public CityService _cityService;

    @Autowired
    public StorageService _storageService;

    @Autowired
    public LocationService _locationService;

    @Autowired
    public CityRepository cityRepository;

    @PostMapping(path="/")
    public ResponseEntity addCity(@ModelAttribute("city") City city, @RequestParam("image") MultipartFile file) throws Exception{
        ResultData resultData=new ResultData();
        try{
            if(!file.isEmpty()){
                this._storageService.storeCity(file);
                this._cityService.createCity(city);
                resultData.success = "saved";
            }
            else{
                this._cityService.createCity(city);
                resultData.success = "saved";
            }
        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity loadAllCity() throws Exception{
        ResultData resultData=new ResultData();
        try{
            List<City> cityData=_cityService.getAllCity();
            List<String> imageList=new ArrayList<>();
            String imageSrc="";
            for (City city : cityData) {
                String cityRootPath = this._storageService.getCityRootPath();
                File file = new File(cityRootPath, city.getCityImageName());
                if(file.exists()){
                    imageSrc = this._storageService.getImageSrc(file);
                }else{
                    File defaultImage = new File(this._storageService.getWorkingPath()+"static/image/","defaultImage.jpeg");
                    if(defaultImage.exists()) {
                        imageSrc = this._storageService.getImageSrc(defaultImage);
                        if (imageSrc != null) {
                            city.setCityImage(imageSrc);
                        }
                    }
                }
                //Resource cityFile = this._storageService.getCityFile(city.getCityImage());
                city.setCityImage(imageSrc);
                int stayCount = this.cityRepository.getPgInCity(city.getCityName());
                city.setTotalStays(stayCount);
            }
            resultData.data=(ArrayList)cityData;
            resultData.total=cityData.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity deleteCity(@PathVariable int cityId) throws Exception{
        ResultData resultData=new ResultData();
        try{
//            Integer cityId=Integer.parseInt(city_id);
            City city = _cityService.getCity(cityId);
            if(city!=null){
                this._cityService.deleteCity(cityId);
                new File(_storageService.getCityRootPath(),city.getCityImageName()).delete();
            }
            resultData.success = "deleted";
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/updateCityWithoutImage")
    public ResponseEntity updateCityWithoutImage(@ModelAttribute("city") City city){
        ResultData result=new ResultData();
        try{
            City cityData=this._cityService.getCity(city.getCityId());
            if(cityData!=null){
                this._cityService.updateCity(city);
                result.success="success";
            }
            else{
                throw new HelperException("City Not Present");
            }
        }
        catch(Exception e){
            result.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/updateCityWithImage")
    public ResponseEntity updateCityWithImage(@ModelAttribute("city") City city, @RequestParam("image") MultipartFile file){
        ResultData result=new ResultData();
        try{
            City cityData=this._cityService.getCity(city.getCityId());
            if(cityData!=null){
                File existingFile=new File(this._storageService.getCityRootPath(), cityData.getCityImageName());
                if(existingFile.exists()){
                    existingFile.delete();
                }
                this._storageService.storeCity(file);
                this._cityService.updateCity(city);
                result.success="success";
            }
            else{
                throw new HelperException("City Not Present");
            }
        }
        catch(Exception e){
            result.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@GetMapping(
            value="/getCity",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getCityData() throws Exception{
        InputStream in=getClass();

    }*/
}
