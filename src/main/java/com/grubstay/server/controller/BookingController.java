package com.grubstay.server.controller;

import com.grubstay.server.entities.Bookings;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAllBookings")
    public ResponseEntity getAllBookings() throws Exception {
        ResultData resultData=new ResultData();
        try{
            List<Bookings> allBookingDetails= bookingService.getAllBookings();
            ArrayList allBookings=new ArrayList();
            allBookings.add(allBookingDetails);

            resultData.data=allBookings;
            resultData.total=allBookingDetails.size();
        }catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
