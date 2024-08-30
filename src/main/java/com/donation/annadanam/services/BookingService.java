package com.donation.annadanam.services;



import com.donation.annadanam.dao.BookingRepository;
import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SlotRepository slotRepository;

    /**
     * Create a new booking for a specified slot ID.
     * @param booking The booking details.
     * @param slotId The slot ID to book.
     * @return The created Booking.
     */
    public Booking createBooking(Booking booking, Long slotId) {
       
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));

        if (slot.isAvailable()) {
            Booking savedBooking = bookingRepository.save(booking);

            // Set the bookingId on the Slot entity
            slot.setBooking(savedBooking);
            slot.setAvailable(false);
            
            slotRepository.save(slot);
            
            return savedBooking;
        }

        
        return null;
    }

    /**
     * Update an existing booking by ID.
     * @param id The ID of the booking to update.
     * @param booking The new booking details.
     * @return The updated Booking.
     */
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        existingBooking.setUser(booking.getUser());
        return bookingRepository.save(existingBooking);
    }

    /**
     * Delete a booking by ID.
     * @param id The ID of the booking to delete.
     */
    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        bookingRepository.delete(existingBooking);
    }

    /**
     * Retrieve a booking by its ID.
     * @param id The ID of the booking to retrieve.
     * @return The Booking object.
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    /**
     * Retrieve all bookings.
     * @return A list of all bookings.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
