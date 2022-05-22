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

    @Override
    public Bookings updateBooking(Bookings booking) throws HelperException{
        Bookings bookingObj=this.bookingRespository.findBookingByBookingID(booking.getBookingID());
        if(bookingObj==null){
            throw new HelperException("Booking doesn't exist");
        }else{
            bookingObj.setStatus(booking.getStatus());
            bookingObj.setBookingStatusReference(booking.getBookingStatusReference());
            bookingObj=this.bookingRespository.save(bookingObj);
        }
        return bookingObj;
    }
}
