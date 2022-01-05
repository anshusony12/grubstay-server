package com.grubstay.server.repos;

import com.grubstay.server.entities.PGRoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PGFacilityRepository extends JpaRepository<PGRoomFacility, Long> {
    @Query("from PGRoomFacility room where room.pgStayId.pgId = ?1")
    public PGRoomFacility findPGRoomFacilityByPgStayId(String pgStayId);
}
