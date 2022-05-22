package com.grubstay.server.repos;

import com.grubstay.server.entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRespository extends JpaRepository<Bookings, Integer> {
    Bookings findByOrderID(String orderID);

    List<Bookings> findByUsernameAndStatus(String username, String status);

    Bookings findBookingByBookingID(Integer bookingID);
}
