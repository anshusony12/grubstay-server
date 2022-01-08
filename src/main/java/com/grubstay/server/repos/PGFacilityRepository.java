package com.grubstay.server.repos;

import com.grubstay.server.entities.PGRoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PGFacilityRepository extends JpaRepository<PGRoomFacility, Integer> {
    @Query("from PGRoomFacility room where room.pgStayId.pgId = ?1")
    public PGRoomFacility findPGRoomFacilityByPgStayId(String pgStayId);

    @Query("delete from PGRoomFacility room where room.rmfId = ?1")
    @Modifying
    @Transactional
    public void deleteRoomFacilityById(int id);
}
