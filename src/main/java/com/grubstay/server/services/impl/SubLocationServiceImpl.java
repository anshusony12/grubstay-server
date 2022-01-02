package com.grubstay.server.services.impl;

import com.grubstay.server.entities.SubLocation;
import com.grubstay.server.repos.SubLocationRepository;
import com.grubstay.server.services.SubLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubLocationServiceImpl implements SubLocationService {

    @Autowired
    private SubLocationRepository _subLocationRepository;

    @Override
    public List<SubLocation> loadAllSubLocation() {
        List<SubLocation> allSubLocationList=null;
        try{
            allSubLocationList=this._subLocationRepository.findAll();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return allSubLocationList;
    }

    @Override
    public SubLocation createSubLocation(SubLocation subLocation) throws Exception {
        SubLocation subLoc=null;
        try{
            subLoc=this._subLocationRepository.save(subLocation);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return subLoc;
    }

    @Override
    public SubLocation subLocaionUsingNameAndLocationId(String subLocationName, Long locationId) {
        SubLocation sublocation=null;
        try{
            sublocation=this._subLocationRepository.subLocaionUsingNameAndLocationId(subLocationName,locationId);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return sublocation;
    }

    @Override
    public boolean updateSubLocation(String subLocationName, boolean status, Long subLocationId) {
        try{
            Integer i=this._subLocationRepository.updateSubLocation(subLocationName, status, subLocationId);
            if(i > 0){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    @Override
    public boolean deleteSubLocation(Long subLocationId) {
        try{
            this._subLocationRepository.deleteSubLocationBySubLocationId(subLocationId);
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return false;
    }
}
