package com.donation.annadanam.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.services.BookingService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin("http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingService bookingService;

	    @PostMapping
	    @RequestMapping("/createbooking")
	    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking,HttpServletRequest request, @RequestParam Long slotId) {
	        Booking createdBooking = bookingService.createBooking(booking,request,slotId);
	        return ResponseEntity.ok(createdBooking);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
	        Booking updatedBooking = bookingService.updateBooking(id, booking);
	        return ResponseEntity.ok(updatedBooking);
	    }
	
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
	        bookingService.deleteBooking(id);
	        return ResponseEntity.noContent().build();
	    }
	
	    @GetMapping("/{id}")
	    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
	        Booking booking = bookingService.getBookingById(id);
	        return ResponseEntity.ok(booking);
	    }
	
	    @GetMapping
	    @RequestMapping("/getallbookings")
	    public ResponseEntity<List<Booking>> getAllBookings() {
	        List<Booking> bookings = bookingService.getAllBookings();
	        return ResponseEntity.ok(bookings);
	    }
}
