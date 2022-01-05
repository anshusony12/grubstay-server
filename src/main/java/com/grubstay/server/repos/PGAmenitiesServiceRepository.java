package com.grubstay.server.repos;

import com.grubstay.server.entities.PGAmenitiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PGAmenitiesServiceRepository extends JpaRepository<PGAmenitiesServices, Long> {

    @Query("from PGAmenitiesServices amen where amen.pgStayId.pgId = ?1")
    public PGAmenitiesServices findPGAmenitiesServicesByPgStayId(String pgStayId);
}
