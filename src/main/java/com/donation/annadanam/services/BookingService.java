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

    public Booking createBooking(Booking booking, HttpServletRequest request, Long slotId) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7);

        String email = jwtService.extractUserName(token);

        Donar donar = donarRepository.findByEmail(email);
        if (donar == null) {
            throw new ResourceNotFoundException("Donar not found with email: " + email);
        }

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + slotId));

        Trust trust = slot.getTrust();
        if (trust == null) {
            throw new IllegalStateException("Trust not associated with the Slot.");
        }

        if (!slot.isAvailable()) {
            throw new IllegalStateException("Slot is not available for booking.");
        }

        booking.setDonar(donar);
        booking.setTrust(trust);

        Booking savedBooking = bookingRepository.save(booking);

        slot.setBooking(savedBooking);
        slot.setAvailable(false);
        slotRepository.save(slot);

        List<Booking> donarBookings = donar.getBookings();
        donarBookings.add(savedBooking);
        donar.setBookings(donarBookings);
        donarRepository.save(donar);

        List<Booking> trustBookings = trust.getBookings();
        trustBookings.add(savedBooking);
        trust.setBookings(trustBookings);
        trustRepository.save(trust);

        return savedBooking; 
    }

    public List<Booking> getAllBookingsofTrust(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7);

        String email = jwtService.extractUserName(token);

        Trust user = trustRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        Trust trust = trustRepository.findByEmail(user.getEmail());
        if (trust == null) {
            throw new ResourceNotFoundException("Trust not found for User with id: " + user.getId());
        }

        List<Booking> bookings = bookingRepository.findByTrustId(trust.getId());
        if (bookings == null || bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for Trust with id: " + trust.getId());
        }

        return bookings;
    }

    public List<Booking> getAllBookingsofDonar(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7);

        String email = jwtService.extractUserName(token);

        Donar user = donarRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        Donar donar = donarRepository.findByEmail(user.getEmail());
        if (donar == null) {
            throw new ResourceNotFoundException("Donar not found for User with id: " + user.getId());
        }

        List<Booking> bookings = bookingRepository.findByDonarId(donar.getId());
        if (bookings == null || bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for Donar with id: " + donar.getId());
        }

        return bookings;
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        existingBooking.setUser(booking.getUser());
        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        bookingRepository.delete(existingBooking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
