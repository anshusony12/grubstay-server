package com.grubstay.server.services;

import com.grubstay.server.entities.Bookings;
import com.grubstay.server.helper.HelperException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookingService {
    public List<Bookings> getAllBookings() throws HelperException;
    public Bookings updateBooking(Bookings booking) throws HelperException;
}
