package com.grubstay.server.repos;

import com.grubstay.server.entities.PGAmenitiesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PGAmenitiesServiceRepository extends JpaRepository<PGAmenitiesServices, Integer> {

    @Query("from PGAmenitiesServices amen where amen.pgStayId.pgId = ?1")
    public PGAmenitiesServices findPGAmenitiesServicesByPgStayId(String pgStayId);

    @Query("delete from PGAmenitiesServices amen where amen.amsId = ?1")
    @Modifying
    @Transactional
    public void deleteAmenServiceById(int id);
}
