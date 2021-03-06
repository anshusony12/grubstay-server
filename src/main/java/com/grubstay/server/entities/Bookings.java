package com.grubstay.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingID;
    private int amount;
    private String paymentID;
    private String orderID;
    private String recipt;
    private String status;
    private String username;
    private String fullname;
    private long phoneNo;
    private String email;
    private String bookingDate;
    private String reserveDate;
    private String sharingType;
    private String pgName;
    private String pgID;
    private String bookingStatus;
    private String bookingStatusReference;

    public String getBookingStatusReference() {
        return bookingStatusReference;
    }

    public void setBookingStatusReference(String bookingStatusReference) {
        this.bookingStatusReference = bookingStatusReference;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRecipt() {
        return recipt;
    }

    public void setRecipt(String recipt) {
        this.recipt = recipt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getSharingType() {
        return sharingType;
    }

    public void setSharingType(String sharingType) {
        this.sharingType = sharingType;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgID() {
        return pgID;
    }

    public void setPgID(String pgID) {
        this.pgID = pgID;
    }

    public Bookings() {
    }

    public Bookings(int bookingID, int amount, String paymentID, String orderID, String recipt, String status, String username, String fullname, long phoneNo, String email, String bookingDate, String reserveDate, String sharingType, String pgName, String pgID, String bookingStatus, String bookingStatusReference) {
        this.bookingID = bookingID;
        this.amount = amount;
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.recipt = recipt;
        this.status = status;
        this.username = username;
        this.fullname = fullname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.bookingDate = bookingDate;
        this.reserveDate = reserveDate;
        this.sharingType = sharingType;
        this.pgName = pgName;
        this.pgID = pgID;
        this.bookingStatus = bookingStatus;
        this.bookingStatusReference=bookingStatusReference;
    }
}
