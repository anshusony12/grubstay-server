package com.grubstay.server.repos;

import com.grubstay.server.entities.PayingGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PGRepository extends JpaRepository<PayingGuest, String> {

    public PayingGuest findPayingGuestByPgId(String pgId);

    @Query("from PayingGuest p where p.pgName = ?1 and p.subLocation.subLocationId = ?2")
    public PayingGuest findPGByNameAndSubLocation(String name, Long sLId);
}
