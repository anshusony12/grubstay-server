package com.grubstay.server.repos;

import com.grubstay.server.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findLocationByLocationId(Long locationId);

    @Modifying
    @Transactional
    @Query("update Location l set l.status = ?1 where l.locationId = ?2")
    public int updateLocationByLocationId(boolean status, Long locationId);
}
