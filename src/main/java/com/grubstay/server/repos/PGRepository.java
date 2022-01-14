package com.grubstay.server.repos;

import com.grubstay.server.entities.PayingGuest;
import com.grubstay.server.entities.StayGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PGRepository extends JpaRepository<PayingGuest, String> {

    public PayingGuest findPayingGuestByPgId(String pgId);

    @Query("from PayingGuest p where p.pgName = ?1 and p.subLocation.subLocationId = ?2")
    public PayingGuest findPGByNameAndSubLocation(String name, Long sLId);

    @Query("delete from PayingGuest pg where pg.pgId = ?1")
    @Modifying
    @Transactional
    public void deletePgById(String pgId);

    @Query("from PayingGuest p where p.subLocation.subLocationId=?1")
    public List<PayingGuest> findPayingGuestBySubLocationId(Long subLocationId);

    @Query(value="from StayGallery  s where s.stayId=?1 and s.galName like '%pgmain%'")
    public  StayGallery findFirstByStayId(String stayId);
}
