package com.grubstay.server.repos;

import com.grubstay.server.entities.PayingGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PGRepository extends JpaRepository<PayingGuest, String> {

    public PayingGuest findPayingGuestByPgId(String pgId);
}
