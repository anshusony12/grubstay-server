package com.grubstay.server.services.impl;

import com.grubstay.server.entities.LandMarks;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.repos.LandMarkRepository;
import com.grubstay.server.services.LandMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LandMarkServiceImpl implements LandMarkService {

    @Autowired
    private LandMarkRepository _landMarkRepository;

    @Override
    public LandMarks findLandMarksByLandMarkNameAndPgStayId(String landMarkName, String pgId) {
        LandMarks landMarks=null;
        try{
            landMarks=this._landMarkRepository.findLandMarksByLandMarkNameAndPgStayId(landMarkName, pgId);
            if(landMarks!=null){
                return landMarks;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    @Override
    public List deleteLandMarkById(int landMarkId) throws Exception{
        List msgData=null;
        boolean deleteStatus=false;
        try{
            LandMarks landMark=this._landMarkRepository.findLandMarksByLandMarkId(landMarkId);
            if(landMark!=null){
                msgData=new ArrayList();
                msgData.add(0,landMark);
                this._landMarkRepository.deleteByLandMarkId(landMarkId);
                LandMarks deleteLandMark=this._landMarkRepository.findLandMarksByLandMarkId(landMarkId);
                if(deleteLandMark==null){
                    msgData.add(1, true);
                }
                else{
                    msgData.add(1, false);
                }
            }else{
                throw new HelperException("Landmark not found!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return msgData;
    }
}
