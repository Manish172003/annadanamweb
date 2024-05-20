package com.donation.annadanam.services;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.donation.annadanam.entities.Booking;

public interface BookingService {
    Booking createBooking(Booking booking, Long slotId);
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
//    ResponseEntity<Object>  createBooking(Booking booking, Long slotId);
}
