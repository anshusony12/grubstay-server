package com.grubstay.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubstay.server.entities.*;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.*;
import com.grubstay.server.services.PGService;
import com.grubstay.server.services.StorageService;
import com.grubstay.server.services.SubLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pg")
@CrossOrigin(origins = "*")
public class PGController {

    @Autowired
    private PGService _pgService;

    @Autowired
    private PGRepository pgRepository;

    @Autowired
    private SubLocationService _subLocationService;

    @Autowired
    private SubLocationRepository subLocationRepository;

    @Autowired
    private PGAmenitiesServiceRepository pgAmenitiesServiceRepository;

    @Autowired
    private PGFacilityRepository pgFacilityRepository;

    @Autowired
    private StayGalleryRepository stayGalleryRepository;

    @Autowired
    private StorageService storageService;

    @PostMapping(path="/")
    public ResponseEntity addPg(@ModelAttribute("pg") PayingGuest pg,
                                @RequestParam("pgImages[]") MultipartFile[] pgImages,
                                @RequestParam("amens") String amens,
                                @RequestParam("roomFacs")String roomFacs, @RequestParam("subLocationId") String subLocationId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            PGAmenitiesServices pgAmen = new ObjectMapper().readValue(amens, PGAmenitiesServices.class);
            PGRoomFacility pgRoomFacs = new ObjectMapper().readValue(roomFacs, PGRoomFacility.class);
            //System.out.println(pg);
            Long sLId = Long.parseLong(subLocationId);
            PayingGuest pgExist = this._pgService.findPGByNameAndSubLocation(pg.getPgName(), sLId);
            if(pgExist==null){
                if(sLId!=0){
                    SubLocation subLocation = this.subLocationRepository.findSubLocationBySubLocationId(sLId);
                    if(subLocation!=null){
                        pg.setSubLocation(subLocation);
                        PayingGuest pgSaved = this._pgService.createPG(pg);
                        if(pgSaved!=null){
                            pgAmen.setPgStayId(pgSaved);
                            pgRoomFacs.setPgStayId(pgSaved);
                            this.pgAmenitiesServiceRepository.save(pgAmen);
                            this.pgFacilityRepository.save(pgRoomFacs);
                            if(pgImages.length > 0){
                                for(MultipartFile file: pgImages){
                                    if(!file.isEmpty()){
                                        StayGallery stayGallery = new StayGallery();
                                        stayGallery.setGalName(file.getOriginalFilename());
                                        stayGallery.setStayId(pgSaved.getPgId());
                                        StayGallery savedStayGallery = this.stayGalleryRepository.save(stayGallery);
                                        if(savedStayGallery!=null){
                                            this.storageService.storePg(file);

                                        }
                                    }
                                }
                            }
                            resultData.success = "saved";
                        }
                    }
                }
            }
            else {
                resultData.success = "duplicate";
            }

        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PutMapping(path="/")
    public ResponseEntity updatePg(@ModelAttribute("pg") PayingGuest pg,
                                @RequestParam("pgImages[]") MultipartFile[] pgImages,
                                @RequestParam("amens") String amens,
                                @RequestParam("roomFacs")String roomFacs, @RequestParam("subLocationId") String subLocationId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            PGAmenitiesServices pgAmen = new ObjectMapper().readValue(amens, PGAmenitiesServices.class);
            PGRoomFacility pgRoomFacs = new ObjectMapper().readValue(roomFacs, PGRoomFacility.class);
            PayingGuest existPg = this.pgRepository.findPayingGuestByPgId(pg.getPgId());
            existPg=pg;
            existPg.setSubLocation(this.subLocationRepository.findSubLocationBySubLocationId(Long.parseLong(subLocationId)));
            PayingGuest updatedPG = this.pgRepository.save(existPg);
            if(updatedPG!=null) {
                pgAmen.setPgStayId(updatedPG);
                pgRoomFacs.setPgStayId(updatedPG);
                this.pgAmenitiesServiceRepository.save(pgAmen);
                this.pgFacilityRepository.save(pgRoomFacs);
                if(pgImages.length > 0){
                    List<StayGallery> stayGalleries = this.stayGalleryRepository.getStayGalleryByPgId(updatedPG.getPgId());
                    this.stayGalleryRepository.deletByStayId(updatedPG.getPgId());
                    for(StayGallery stay : stayGalleries){
                        String fileName = stay.getGalName();
                        new File(this.storageService.getPgRootPath(),fileName).delete();
                    }
                    for(MultipartFile file: pgImages){
                        if(!file.isEmpty()){
                            StayGallery stayGallery = new StayGallery();
                            stayGallery.setGalName(file.getOriginalFilename());
                            stayGallery.setStayId(updatedPG.getPgId());
                            StayGallery savedStayGallery = this.stayGalleryRepository.save(stayGallery);
                            if(savedStayGallery!=null){
                                this.storageService.storePg(file);
                            }
                        }
                    }
                }
                resultData.success="success";
            }

        }
        catch(Exception e){
            resultData.error=e.toString();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity loadAllPGData() throws Exception{
        ResultData resultData=new ResultData();
        try{
            List<PayingGuest> payingGuests = this._pgService.loadAllPGData();
            resultData.data=(ArrayList)payingGuests;
            resultData.total=payingGuests.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/pgGallery/{pgId}")
    public ResponseEntity loadAllPGGallery(@PathVariable("pgId") String pgId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            List<StayGallery> galleryList = this.stayGalleryRepository.getStayGalleryByPgId(pgId);
            List<String> imageList=new ArrayList<>();
            for (StayGallery stay : galleryList) {
                String imageSrc="";
                String pgRootPath = this.storageService.getPgRootPath();
                File file = new File(pgRootPath, stay.getGalName());
                //File defaultPg = new File(pgRootPath, "defaultcity.jpeg");
                if(file.exists()){
                    imageSrc = this.storageService.getImageSrc(file);
                }
                //Resource cityFile = this._storageService.getCityFile(city.getCityImage());
                imageList.add(imageSrc);
            }
            resultData.data=(ArrayList)imageList;
            resultData.total=imageList.size();
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
