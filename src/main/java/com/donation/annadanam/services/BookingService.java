package com.donation.annadanam.services;


import java.util.List;

import com.donation.annadanam.entities.Booking;

public interface BookingService {
    Booking addBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
}
