package com.donation.annadanam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.services.BookingService;
import com.donation.annadanam.services.DonarService;
import com.donation.annadanam.services.TrustService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/donar")
@CrossOrigin("http://localhost:3000")
public class DonarController {

    @Autowired
    private DonarService donarService;
    @Autowired
    private BookingService bookingService;
   

    
    @GetMapping
    @RequestMapping("/getbookings")
    public ResponseEntity<List<Booking>> getBookings(HttpServletRequest request) 
    {
        List<Booking> bookings = bookingService.getAllBookingsofDonar(request);
        return ResponseEntity.ok(bookings);
    }
    
   
}

