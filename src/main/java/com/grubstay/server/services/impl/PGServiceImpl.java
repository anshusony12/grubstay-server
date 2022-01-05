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

    public PayingGuest createPG(PayingGuest pg) throws PGFoundException {
        PayingGuest existingPG=pgRepository.findPayingGuestByPgId(pg.getPgId());
        PayingGuest savedPg = null;
        if(existingPG!=null){
            throw new PGFoundException();
        }else{
            savedPg = pgRepository.save(pg);
        }
        return savedPg;
    }

    @Override
    public PayingGuest findPGByNameAndSubLocation(String name, Long sLId) {
        PayingGuest pg = null;
        try {
            pg = this.pgRepository.findPGByNameAndSubLocation(name, sLId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pg;
    }

    @Override
    public List<PayingGuest> loadAllPGData() {
        List<PayingGuest> pgList = this.pgRepository.findAll();
        return pgList;
    }

}
