package com.grubstay.server.services.impl;

import com.grubstay.server.entities.*;
import com.grubstay.server.helper.PGFoundException;
import com.grubstay.server.repos.PGRepository;
import com.grubstay.server.services.PGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PGServiceImpl implements PGService {

    @Autowired
    PGRepository pgRepository;

    public PayingGuest createPG(PayingGuest pg, PGAmenitiesServices amenitiesServices, PGRoomFacility roomFacility, List<LandMarks> landMarks) throws PGFoundException {
        PayingGuest existingPG=pgRepository.findPayingGuestByPgId(pg.getPgId());
        if(existingPG!=null){
            throw new PGFoundException();
        }else{
            pg.setAmenitiesServices(amenitiesServices);
            pg.setRoomFacility(roomFacility);
            pg.setLandMarksList(landMarks);
            pgRepository.save(pg);
        }
        return null;
    }
}
