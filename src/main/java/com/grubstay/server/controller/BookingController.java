package com.grubstay.server.controller;

import com.grubstay.server.entities.Bookings;
import com.grubstay.server.helper.ResultData;
import com.grubstay.server.repos.BookingRespository;
import com.grubstay.server.services.BookingService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRespository bookingRespository;

    @Value("${reserve.bookingAmount}")
    private String bookingAmount;

    @Value("${reserve.recieptName}")
    private String reciept;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razopaySecret;

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

    @PostMapping("/reservePg")
    public ResponseEntity reservePgNow(@RequestBody Map<String,Object> booking){
        ResultData resultData = new ResultData();

        try{
            Bookings reserve = new Bookings();
            reserve.setBookingDate(booking.get("bookingDate").toString());
            reserve.setAmount(Integer.parseInt(this.bookingAmount));
            reserve.setReserveDate(booking.get("reserveDate").toString());
            reserve.setEmail(booking.get("email").toString());
            reserve.setUsername(booking.get("username").toString());
            reserve.setFullname(booking.get("fullName").toString());
            reserve.setPgID(booking.get("pgId").toString());
            reserve.setPgName(booking.get("pgName").toString());
            reserve.setPhoneNo(Long.parseLong(booking.get("phoneNo").toString()));
            reserve.setSharingType(booking.get("sharingType").toString());
            reserve.setRecipt(this.reciept.concat(booking.get("bookingDate").toString()));

            RazorpayClient razorpayClient = new RazorpayClient(this.razorpayKey, this.razopaySecret);

            JSONObject obj = new JSONObject();
            obj.put("amount",Integer.parseInt(this.bookingAmount)*100);
            obj.put("currency","INR");
            obj.put("receipt",this.reciept.concat(booking.get("bookingDate").toString()));

            //creating new razorpay order request
            Order order = razorpayClient.orders.create(obj);

            //saving created order on db
            reserve.setOrderID(order.get("id").toString());
            reserve.setStatus(order.get("status").toString().toUpperCase());
            this.bookingRespository.save(reserve);
            ArrayList<Object> list = new ArrayList<>();
            list.add(order.toString());
            list.add(this.razorpayKey);
            resultData.data = list;
            resultData.success = "created";
            resultData.total = list.size();
        }catch (Exception e){
            resultData.error = e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData,HttpStatus.OK);
    }

    @PostMapping("/update-status")
    public ResponseEntity updateBooking(@RequestBody Map<String,String> data){
        ResultData resultData = new ResultData();
        try{
            Bookings order = this.bookingRespository.findByOrderID(data.get("order_id").toString());
            order.setStatus(data.get("status").toString().toUpperCase());
            if(data.get("payment_id")!=null){
                order.setPaymentID(data.get("payment_id").toString());
            }
            if(data.get("status").equals("PAID")){
                order.setBookingStatus("BOOKED");
            }else if(data.get("status").equals("FAILED")){
                order.setBookingStatus("BOOKING_ATTEMPTED");
            }
            Bookings save = this.bookingRespository.save(order);
            ArrayList<Object> list = new ArrayList<>();
            list.add(save);
            resultData.data = list;
            resultData.success = "updated";
            resultData.total = list.size();
        }catch (Exception e){
            e.printStackTrace();
            resultData.error = e.getMessage();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PostMapping("/my-bookings")
    public ResponseEntity updateBooking(@RequestBody String username){
        ResultData resultData = new ResultData();
        try{
            List<Bookings> myBookings = this.bookingRespository.findByUsernameAndStatus(username, "PAID");
            ArrayList<Object> list = new ArrayList<>();
            list.add(myBookings);
            resultData.data = list;
            resultData.success = "fetched";
            resultData.total = list.size();
        }catch (Exception e){
            e.printStackTrace();
            resultData.error = e.getMessage();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
    @PostMapping("/cancel-booking")
    public ResponseEntity cancelBookingByOrderId(@RequestBody String orderId){
        ResultData resultData = new ResultData();
        try{
            Bookings order = this.bookingRespository.findByOrderID(orderId);
            order.setBookingStatus("CANCELLED_BY_USER");
            this.bookingRespository.save(order);
            resultData.success = "cancelled";
        }catch (Exception e){
            e.printStackTrace();
            resultData.error = e.getMessage();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }

    @PostMapping("/updateBooking")
    public ResponseEntity updateBooking(@RequestBody Bookings booking){
        ResultData resultData = new ResultData();
        try{
            Bookings updatedBookingObj=this.bookingService.updateBooking(booking);
            if(updatedBookingObj!=null){
                resultData.success="BOOKING_UPDATED";
            }else{
                resultData.error="BOOKING_NOT_EXIST";
            }
        }
        catch(Exception e){
            resultData.error=e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
