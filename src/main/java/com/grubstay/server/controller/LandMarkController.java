package com.grubstay.server.controller;

import com.grubstay.server.entities.LandMarks;
import com.grubstay.server.entities.PayingGuest;
import com.grubstay.server.entities.StayGallery;
import com.grubstay.server.entities.SubLocation;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.LandMarkRepository;
import com.grubstay.server.repos.PGRepository;
import com.grubstay.server.repos.SubLocationRepository;
import com.grubstay.server.services.LandMarkService;
import com.grubstay.server.services.StorageService;
import com.grubstay.server.services.SubLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/landmark")
@CrossOrigin(origins = "*")
public class LandMarkController {

    @Autowired
    private LandMarkRepository _landMarkRepository;

    @Autowired
    private LandMarkService _landMarkService;

    @Autowired
    private PGRepository _pgRespository;

    @Autowired
    private StorageService _storageService;

    @PostMapping(path = "/")
    public ResponseEntity addLandMark(@ModelAttribute("landmark") LandMarks landmark,
                                      @RequestParam("landmarkimage") MultipartFile landMarkImage,
                                      @RequestParam("cityId") Integer cityId,
                                      @RequestParam("locationId") Integer locationId,
                                      @RequestParam("subLocationId") Integer subLocationId,
                                      @RequestParam("stayId") String pgId){
        ResultData resultData=new ResultData();
        try{
            PayingGuest payingGuest=this._pgRespository.findPayingGuestByPgId(pgId.toString());
            if (payingGuest!=null){
                LandMarks landMarks=this._landMarkService.findLandMarksByLandMarkNameAndPgStayId(landmark.getLandMarkName(), pgId);
                if(landMarks==null) {
                    if (!landMarkImage.isEmpty()) {
                        this._storageService.storeLandMark(landMarkImage);
                        File file = new File(this._storageService.getLandMarkPath(), landMarkImage.getOriginalFilename());
                        if (file != null) {
                            landmark.setLandMarkImageName(landMarkImage.getOriginalFilename());
                            landmark.setPgStayId(payingGuest);
                            LandMarks savedlandMark = this._landMarkRepository.save(landmark);
                            if (savedlandMark != null) {
                                resultData.success = "saved";
                            } else {
                                resultData.success = "failed";
                            }
                        }
                    }
                }
                else{
                    resultData.success="failed";
                    throw new HelperException("LandMark Already Added!");
                }

            }
            else{
                resultData.success="failed";
                throw new HelperException("PG not present!");
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity loadAllLandMarks(){
        ResultData resultData=new ResultData();
        try{
            List<LandMarks> landMarksData=this._landMarkRepository.findAll();
            if(landMarksData.size() > 0){
                for(LandMarks landMark : landMarksData){
                    String landMarkImageName=landMark.getLandMarkImageName();
                    if(landMarkImageName!=null){
                        File file=new File(this._storageService.getLandMarkPath(), landMarkImageName);
                        if(file.exists()){
                            String imageSrc=this._storageService.getImageSrc(file);
                            if(imageSrc!=null){
                                landMark.setLandMarkImage(imageSrc);
                            }
                        }
                        else{
                            File defaultImage=new File(this._storageService.getLandMarkPath(), "default_landmark.jpeg");
                            if(defaultImage.exists()){
                                String imageSrc=this._storageService.getImageSrc(defaultImage);
                                if(imageSrc!=null){
                                    landMark.setLandMarkImage(imageSrc);
                                }
                            }
                        }
                    }
                }
                resultData.data=(ArrayList)landMarksData;
                resultData.success="Fetched";
            }
            else{
                resultData.total=0;
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
    @DeleteMapping(path="/{landMarkId}")
    public ResponseEntity deleteLandMark(@PathVariable("landMarkId") Integer landMarkId){
        ResultData resultData=new ResultData();
        try{
            List deletedData=this._landMarkService.deleteLandMarkById(landMarkId);
            if(deletedData!=null){
                if(deletedData.get(1).toString().equals("true")){
                    LandMarks deletedLandMark=(LandMarks)deletedData.get(0);
                    String landMarkImageName=deletedLandMark.getLandMarkImageName();
                    if(landMarkImageName!=null) {
                        File file = new File(this._storageService.getLandMarkPath(), landMarkImageName);
                        if(file.exists()) {
                            file.delete();
                        }
                    }
                    resultData.success="deleted";
                }
                else{
                    throw new HelperException("LandMark Deletion Failed!");
                }
            }
            else{
                throw new HelperException("LandMark Deletion Failed!");
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity updateLandmark(@RequestParam("landmarkimage") MultipartFile landMarkImage,
                                         @RequestParam("landMarkId") Integer landMarkId){
        ResultData resultData=new ResultData();
        try{
            LandMarks existingLandMark=this._landMarkRepository.findLandMarksByLandMarkId(landMarkId);
            if(existingLandMark!=null){
                if(!landMarkImage.isEmpty()){
                    String oldImageName=existingLandMark.getLandMarkImageName();
                    existingLandMark.setLandMarkImageName(landMarkImage.getOriginalFilename());
                    LandMarks updateLandMark=this._landMarkRepository.save(existingLandMark);
                    if(updateLandMark!=null) {
                        File file = new File(this._storageService.getLandMarkPath(),oldImageName);
                        boolean deleteStatus = file.delete();
                        if (deleteStatus == true) {
                            this._storageService.storeLandMark(landMarkImage);
                        }
                    }else{
                        throw new HelperException("Landmark Updation Failed!");
                    }
                }
                resultData.success="updated";
            }
            else{
                throw new HelperException("Landmark Not Present!");
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
