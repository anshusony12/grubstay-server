package com.grubstay.server.services;


import com.grubstay.server.entities.Location;
import com.grubstay.server.entities.SubLocation;

import java.util.List;

public interface SubLocationService {
    public List<SubLocation> loadAllSubLocation();
    public SubLocation createSubLocation(SubLocation subLocation) throws Exception;
    public SubLocation subLocaionUsingNameAndLocationId(String subLocationName, Long locationId);
    public boolean updateSubLocation(String subLocationName, boolean status, Long subLocationId);
    public boolean deleteSubLocation(Long subLocationId);
}
