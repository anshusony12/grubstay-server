package com.grubstay.server.repos;

import com.grubstay.server.entities.PGRoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PGFacilityRepository extends JpaRepository<PGRoomFacility, Long> {
}
