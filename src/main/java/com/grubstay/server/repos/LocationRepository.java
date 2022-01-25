package com.grubstay.server.repos;

import com.grubstay.server.entities.City;
import com.grubstay.server.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findLocationByLocationId(Long locationId);

    @Modifying
    @Transactional
    @Query("update Location l set l.status = ?1 where l.locationId = ?2")
    public int updateLocationByLocationId(boolean status, Long locationId);

    @Query("select l from Location l where l.locationName = ?1 and l.city.cityId = ?2")
    public Location locationUsingCityAndLocationName(String locationName, Integer city);

    @Query(value = "select distinct count(location_id) from location",nativeQuery = true)
    public int getLocationsCount();
}
