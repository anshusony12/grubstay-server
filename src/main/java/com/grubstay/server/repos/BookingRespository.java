package com.grubstay.server.repos;

import com.grubstay.server.entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRespository extends JpaRepository<Bookings, Long> {
}
