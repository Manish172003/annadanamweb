package com.donation.annadanam.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.BookingRepository;
import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.exception.ResourceNotFoundException;
import com.donation.annadanam.services.BookingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SlotRepository slotRepository;

//    @Override
//    public Booking addBooking(Booking booking) {
//        return bookingRepository.save(booking);
//    }
//    @Override
//    public Booking createBooking(Booking booking, Long slotId) {
//        // Retrieve the Slot entity by its ID
//        Slot slot = slotRepository.findById(slotId)
//                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));
//        
//        if(slot.isAvailable())
//        {
//        	Booking savedBooking = bookingRepository.save(booking);
//        	
//        	// Set the bookingId on the Slot entity
//        	slot.setBooking(savedBooking);
//        	slot.setAvailable(false);
//        	
//        	slotRepository.save(slot);
//        	
//        	return savedBooking;
//        	
//        }
//        
//        return new ResponseEntity<>("Slot is already booked");
////        if(slot.get)
//        // Save the booking to generate its ID
//    }
    
    @Override
    public Booking createBooking(Booking booking, Long slotId) {
        // Retrieve the Slot entity by its ID
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));
        
        if (slot.isAvailable()) {
            Booking savedBooking = bookingRepository.save(booking);
            
            // Set the bookingId on the Slot entity
            slot.setBooking(savedBooking);
            slot.setAvailable(false);
            
            slotRepository.save(slot);
            
            return booking;
        } 
        
        return null;
    }


    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        existingBooking.setUser(booking.getUser());
//        existingBooking.setSlot(booking.getSlots());
        return bookingRepository.save(existingBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        bookingRepository.delete(existingBooking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
