package com.grubstay.server.services.impl;

import com.grubstay.server.entities.Bookings;
import com.grubstay.server.helper.HelperException;
import com.grubstay.server.repos.BookingRespository;
import com.grubstay.server.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRespository bookingRespository;

    @Override
    public List<Bookings> getAllBookings() throws HelperException {
        return bookingRespository.findAll();
    }
}
