package com.grubstay.server.services;

import com.grubstay.server.entities.LandMarks;
import com.grubstay.server.entities.PGAmenitiesServices;
import com.grubstay.server.entities.PGRoomFacility;
import com.grubstay.server.entities.PayingGuest;
import com.grubstay.server.helper.PGFoundException;

import java.util.List;

public interface PGService {
    public PayingGuest createPG(PayingGuest pg) throws PGFoundException;
    public PayingGuest findPGByNameAndSubLocation(String name, Long sLId);
    public List<PayingGuest> loadAllPGData();
    public List<PayingGuest> findPayingGuestBySubLocationId(long subLocationId);
}
