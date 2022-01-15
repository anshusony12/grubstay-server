package com.grubstay.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grubstay.server.entities.*;
import com.grubstay.server.helper.HelperException;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private LandMarkRepository landMarkRepository;

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
                PGAmenitiesServices oldAmen = this.pgAmenitiesServiceRepository.findPGAmenitiesServicesByPgStayId(updatedPG.getPgId());
                PGRoomFacility oldRoom = this.pgFacilityRepository.findPGRoomFacilityByPgStayId(updatedPG.getPgId());
                pgAmen.setAmsId(oldAmen.getAmsId());
                pgRoomFacs.setRmfId(oldRoom.getRmfId());
                pgAmen.setPgStayId(updatedPG);
                pgRoomFacs.setPgStayId(updatedPG);
                oldAmen = pgAmen;
                oldRoom = pgRoomFacs;
                this.pgAmenitiesServiceRepository.save(oldAmen);
                this.pgFacilityRepository.save(oldRoom);
                if(pgImages.length > 0 && !pgImages[0].getOriginalFilename().equals("noImage")){
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

    @DeleteMapping("/{pgId}")
    public ResponseEntity deletePgData(@PathVariable("pgId") String pgId) throws Exception{
        ResultData resultData=new ResultData();
        try{
            PayingGuest pg = this.pgRepository.findPayingGuestByPgId(pgId);
            PGAmenitiesServices pgAmen = this.pgAmenitiesServiceRepository.findPGAmenitiesServicesByPgStayId(pgId);
            PGRoomFacility pgRoom = this.pgFacilityRepository.findPGRoomFacilityByPgStayId(pgId);
            List<StayGallery> stays = this.stayGalleryRepository.getStayGalleryByPgId(pgId);
            this.pgAmenitiesServiceRepository.deleteAmenServiceById(pgAmen.getAmsId());
            this.pgFacilityRepository.deleteRoomFacilityById(pgRoom.getRmfId());
            this.stayGalleryRepository.deletByStayId(pgId);
            this.pgRepository.deletePgById(pgId);
            for(StayGallery s : stays){
                String fileName = s.getGalName();
                new File(this.storageService.getPgRootPath(),fileName).delete();
            }
            resultData.success = "deleted";
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
                }else{
                    File defaultImage = new File("src/main/resources/static/image/","defaultImage.jpeg");
                    if(defaultImage.exists()){
                         imageSrc=this.storageService.getImageSrc(defaultImage);
                        if(imageSrc!=null){
                            imageList.add(imageSrc);
                        }
                    }
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
    @PostMapping("pgfilterData")
    public ResponseEntity getPGDataWithFilter(@RequestBody String data){
        ResultData resultData=new ResultData();
        HashMap mapData=new HashMap();
        String pgID=null;
        Long locationId=0l;
        final Long location_id;
        final String user_gender;
        final String stay_plan;
        final String near_by;
        String locationName=null;
        String gender=null;
        String stayPlan=null;
        String nearBy=null;
        List<PayingGuest> allPgData=null;
        List<String> pgIdList=null;
        List<PayingGuest> finalPgData=new ArrayList<>();
        try{
            try{
                mapData=new ObjectMapper().readValue(data, HashMap.class);
                if(mapData.get("locationId")!=null) {
                    locationId = Long.parseLong(mapData.get("locationId").toString());
                }
                if(mapData.get("gender")!=null) {
                    gender =mapData.get("gender").toString();
                }
                if(mapData.get("nearby")!=null){
                    nearBy=mapData.get("nearby").toString();
                }
                if(mapData.get("stayPlan")!=null){
                    stayPlan=mapData.get("stayPlan").toString();
                }
            }
            catch(Exception e){
                resultData.error="Unable to Parse Data!";
                e.printStackTrace();
            }
            location_id=locationId;
            user_gender=gender;
            stay_plan=stayPlan;
            near_by=nearBy;
            if(location_id!=0){
                List<SubLocation> allSubLocation=this.subLocationRepository.findAll();
                List<SubLocation> filteredSubLocation=allSubLocation.stream().filter(s -> s.getLocation().getLocationId()==location_id).collect(Collectors.toList());
                allPgData=new ArrayList<>();
                for(SubLocation subLocation:filteredSubLocation){
                    List<PayingGuest> pgData=subLocation.getPayingGuestList();
                    allPgData.addAll(pgData);
                }
                if(user_gender!=null && user_gender!="") {
                    allPgData=allPgData.stream().filter(pg -> pg.getPgGender().equals(user_gender)).collect(Collectors.toList());
                }
                if(stay_plan!=null && stay_plan!=""){
                    if(stay_plan.equals("Monthly")){
                        allPgData=allPgData.stream().filter(pg -> pg.isMonthly() == true).collect(Collectors.toList());
                    }
                    else if(stay_plan.equals("Weekly")){
                        allPgData=allPgData.stream().filter(pg -> pg.isWeekly() == true).collect(Collectors.toList());
                    }
                    else if(stay_plan.equals("Daily")){
                        allPgData= allPgData.stream().filter(pg -> pg.isDaily() == true).collect(Collectors.toList());
                    }
                }
                if(near_by!=null && near_by!=""){
                    pgIdList=new ArrayList();
                    for(PayingGuest pg: allPgData){
                        List<LandMarks> allLandMarks=pg.getLandMarksList();
                        List<LandMarks> filteredLandMarkList=allLandMarks.stream().filter(lm -> lm.getLandMarkName().equalsIgnoreCase(near_by) && lm.getPgStayId().getPgId().equals(pg.getPgId())).collect(Collectors.toList());
                        if(filteredLandMarkList.size() > 0){
                            pgIdList.add(pg.getPgId());
                        }
;                    }
                }
                List<PayingGuest> filteredPgData=null;
                if(pgIdList!=null){
                    if(pgIdList.size() > 0){
                        for(String pgId: pgIdList) {
                            filteredPgData = allPgData.stream().filter(pg -> pg.getPgId().equals(pgId)).collect(Collectors.toList());
                            finalPgData.addAll(filteredPgData);
                        }
                        allPgData=finalPgData;
                    }
                }
                if(allPgData.size() > 0){
                    for(PayingGuest pg : allPgData){
                        StayGallery galleryData = this.pgRepository.findFirstByStayId(pg.getPgId());
                        //StayGallery galleryData=stayGalleryList.get(0);
                        if(galleryData!=null){
                            File file=new File(this.storageService.getPgRootPath(), galleryData.getGalName());
                            if(file.exists()){
                                String imageSrc=this.storageService.getImageSrc(file);
                                if(imageSrc!=null){
                                    pg.setPgImage(imageSrc);
                                    pg.setPgImageName(galleryData.getGalName());
                                }
                            }
                            else{
//                                File defaultImage=new File(this.storageService.getLandMarkPath(), "defaultLandMark.jpg");
//                                if(defaultImage.exists()){
//                                    String imageSrc=this.storageService.getImageSrc(defaultImage);
//                                    if(imageSrc!=null){
//                                        pg.setPgImage(imageSrc);
//                                        pg.setPgImageName("defaultLandMark.jpg");
//                                    }
//                                }
                                File defaultImage = new File("src/main/resources/static/image/","defaultImage.jpeg");
                                if(defaultImage.exists()){
                                    String imageSrc=this.storageService.getImageSrc(defaultImage);
                                    if(imageSrc!=null){
                                        pg.setPgImage(imageSrc);
                                        pg.setPgImageName("defaultLandMark.jpg");
                                    }
                                }
                            }
                        }
                    }
                }
//                if(near_by!=null && near_by!=""){
//                    List<LandMarks> allFilteredLandMarksData=new ArrayList<>();
//                    for(PayingGuest payingGuest: allPgData){
//                        List<LandMarks> allLandMarks=this.landMarkRepository.findLandMarksByPgStayId(payingGuest.getPgId());
//                        if(allLandMarks.size()>0){
//                               List<LandMarks> filteredLandMark=allLandMarks.stream().filter(lm -> lm.getLandMarkName().equals(near_by)).collect(Collectors.toList());
//                               allFilteredLandMarksData.addAll(filteredLandMark);
//                        }
//                    }
//                    List<PayingGuest> pgFilteredUsingLandMarks=new ArrayList<>();
//                    if(allFilteredLandMarksData.size() >  0){
//                        for(LandMarks landMark : allFilteredLandMarksData){
//                            pgFilteredUsingLandMarks= allPgData.stream().filter(pg -> pg.getPgId()==landMark.getPgStayId().getPgId()).collect(Collectors.toList());
//                            allPgData.addAll(pgFilteredUsingLandMarks);
//                        }
//                    }
//                }
            }
            resultData.success="Fetched";
            resultData.data=(ArrayList) allPgData;
        }
        catch(Exception e1){
            resultData.error=e1.getMessage();
            e1.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @GetMapping("/{pgId}")
    public ResponseEntity getPgDetails(@PathVariable("pgId") String pgId){
            ResultData resultData=new ResultData();
            try{
                PayingGuest pg=this.pgRepository.findPayingGuestByPgId(pgId);
                if(pg!=null){
                    ArrayList pgData=new ArrayList();
                    pgData.add(pg);
                    resultData.data=pgData;
                }
                else{
                    throw new HelperException("PG Not Found!");
                }
            }
            catch(Exception e){
                resultData.error=e.getMessage();
                e.printStackTrace();
            }
            return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
    @GetMapping("landmarks/{pgId}")
    public ResponseEntity getLandmarkDetails(@PathVariable("pgId") String pgId){
        ResultData resultData=new ResultData();
        try{
            List<LandMarks> landMarkData=this.landMarkRepository.findLandMarksByPgStayId(pgId);
            int count=0;
            if(landMarkData.size() > 0){
                for(LandMarks landMark : landMarkData){
                    String landMarkImage=landMark.getLandMarkImageName();
                    if(landMarkImage!=null){
                        File file=new File(this.storageService.getLandMarkPath(), landMarkImage);
                        if(file.exists()){
                            String landImageSrc=this.storageService.getImageSrc(file);
                            if(landImageSrc!=null){
                                landMark.setLandMarkImage(landImageSrc);
                                count++;
                            }
                        }
                        else{
                            /*File file1=new File(this.storageService.getLandMarkPath(), "defaultLandMark.jpg");
                            if(file1.exists()){
                                String landImageSrc=this.storageService.getImageSrc(file1);
                                if(landImageSrc!=null){
                                    landMark.setLandMarkImage(landImageSrc);
                                    count++;
                                }
                            }*/
                            File defaultImage = new File("src/main/resources/static/image/","defaultImage.jpeg");
                            if(defaultImage.exists()){
                                String imageSrc=this.storageService.getImageSrc(defaultImage);
                                if(imageSrc!=null){
                                    landMark.setLandMarkImage(imageSrc);
                                    count++;
                                }
                            }
                        }
                    }
                }
                resultData.total=count;
                resultData.data=(ArrayList)landMarkData;
                resultData.success="Found";
            }
            else{
                resultData.total=0;
                resultData.success="Not Found";
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
