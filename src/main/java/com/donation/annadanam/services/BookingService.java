package com.donation.annadanam.services;



import com.donation.annadanam.dao.BookingRepository;
import com.donation.annadanam.dao.DonarRepository;
import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.entities.Donar;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private DonarRepository donarRepository;
    
    @Autowired
    private TrustRepository trustRepository;
    
    
    
    @Autowired
    private JWTService jwtService;

    /**
     * Create a new booking for a specified slot ID.
     * @param booking The booking details.
     * @param slotId The slot ID to book.
     * @return The created Booking.
     */
//    public Booking createBooking(Booking booking,HttpServletRequest request, Long slotId) {
//       
////        Slot slot = slotRepository.findById(slotId)
////                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));
////
////        if (slot.isAvailable()) {
////            Booking savedBooking = bookingRepository.save(booking);
////
////            // Set the bookingId on the Slot entity
////            slot.setBooking(savedBooking);
////            slot.setAvailable(false);
////            
////            slotRepository.save(slot);
////            
////            return savedBooking;
////        }
////
////        
////        return null;
//    	
//    	 String authHeader = request.getHeader("Authorization");
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             throw new IllegalArgumentException("Invalid or missing Authorization header.");
//         }
//         String token = authHeader.substring(7); // Remove "Bearer " prefix
//
//         // Extract email from token
//         String email = jwtService.extractUserName(token); // Assuming extractUsername method fetches email
//
//         // Fetch Donar by email
//         Donar donar = donarRepository.findByEmail(email);
//                
//         System.out.println("this is from booking" + donar);
//         
//         // Fetch Slot by slotId
//         Slot slot = slotRepository.findById(slotId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));
//
//         // Check if the slot is available
//         if (!slot.isAvailable()) {
//             throw new IllegalStateException("Slot is not available for booking.");
//         }
//
//     
//
//         // Save Booking
//         Booking savedBooking = bookingRepository.save(booking);
//
//         // Associate Slot with Booking and mark as unavailable
//         slot.setBooking(savedBooking);
//         slot.setAvailable(false);
//         slotRepository.save(slot);
//
//         return savedBooking;
//    }
    
    public Booking createBooking(Booking booking, HttpServletRequest request, Long slotId) {
        // Extract token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract email from token
        String email = jwtService.extractUserName(token); // Assuming extractUsername method fetches email

        // Fetch Donar by email
        Donar donar = donarRepository.findByEmail(email);
        if (donar == null) {
            throw new ResourceNotFoundException("Donar not found with email: " + email);
        }

        // Fetch Slot by slotId
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));

        // Fetch Trust associated with the Slot
        Trust trust = slot.getTrust();
        if (trust == null) {
            throw new IllegalStateException("Trust not associated with the Slot.");
        }

        // Check if the slot is available
        if (!slot.isAvailable()) {
            throw new IllegalStateException("Slot is not available for booking.");
        }

        // Associate the Donar with the Booking
        booking.setDonar(donar); // Assuming setUser method is used to link Donar
        booking.setTrust(trust);

        // Save Booking
        Booking savedBooking = bookingRepository.save(booking);

        // Associate Slot with Booking and mark as unavailable
        slot.setBooking(savedBooking);
        slot.setAvailable(false);
        slotRepository.save(slot);

        // Add Booking to Donar's bookings list and save Donar
        List<Booking> donarBookings = donar.getBookings();
        donarBookings.add(savedBooking);
        donar.setBookings(donarBookings);
        donarRepository.save(donar);

        // Add Booking to Trust's bookings list and save Trust
        List<Booking> trustBookings = trust.getBookings();
        trustBookings.add(savedBooking);
        trust.setBookings(trustBookings);
        trustRepository.save(trust);

        return savedBooking; 
    }
    
    public List<Booking> getAllBookingsofTrust(HttpServletRequest request) {
        // Extract token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract username from token
        String email = jwtService.extractUserName(token); // Assuming extractUserName method fetches email

        // Fetch User by email
        Trust user = trustRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        // Fetch Trust associated with the User
        Trust trust = trustRepository.findByEmail(user.getEmail());
        if (trust == null) {
            throw new ResourceNotFoundException("Trust not found for User with id: " + user.getId());
        }

        // Fetch Bookings for the Trust
        List<Booking> bookings = bookingRepository.findByTrustId(trust.getId());
        if (bookings == null || bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for Trust with id: " + trust.getId());
        }

        return bookings;
    }
    
    public List<Booking> getAllBookingsofDonar(HttpServletRequest request) {
        // Extract token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Extract username from token
        String email = jwtService.extractUserName(token); // Assuming extractUserName method fetches email

        // Fetch User by email
        Donar user = donarRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        // Fetch Trust associated with the User
        Donar donar = donarRepository.findByEmail(user.getEmail());
        if (donar == null) {
            throw new ResourceNotFoundException("Trust not found for User with id: " + user.getId());
        }

        // Fetch Bookings for the Trust
        List<Booking> bookings = bookingRepository.findByDonarId(donar.getId());
        if (bookings == null || bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for Trust with id: " + donar.getId());
        }

        return bookings;
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
