package com.grubstay.server.repos;

import com.grubstay.server.entities.SubLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface SubLocationRepository extends JpaRepository<SubLocation, Long> {
    public SubLocation findSubLocationBySubLocationId(Long subLocationId);

    @Modifying
    @Transactional
    @Query("update SubLocation sl set sl.subLocationName=?1, sl.status=?2 where sl.subLocationId=?3")
    public int updateSubLocation(String subLocationName, boolean status, Long subLocationId);

    @Modifying
    @Transactional
    @Query("delete from SubLocation sl where sl.subLocationId=?1")
    public int deleteSubLocationBySubLocationId(Long subLocationId);


    @Query("select sl from SubLocation sl where sl.subLocationName=?1 and sl.location.locationId=?2")
    public SubLocation subLocaionUsingNameAndLocationId(String subLocationName, Long subLocationId);

    @Query(value = "select distinct count(sub_location_id) from sub_location",nativeQuery = true)
    public int getSubLocationsCount();
}
