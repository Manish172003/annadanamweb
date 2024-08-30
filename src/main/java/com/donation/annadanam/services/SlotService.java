package com.donation.annadanam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.DonarRepository;
import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.dao.UserRepository;
import com.donation.annadanam.entities.Donar;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.entities.Users;
import com.donation.annadanam.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrustRepository trustRepository;
    
    @Autowired
    private DonarRepository donarRepository;

    @Autowired
    private JWTService jwtService;
    /**
     * Adds a new slot to the database.
     * @param slot The slot entity to add.
     * @return The added Slot entity.
     */
    public Slot addSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    /**
     * Adds a slot to a specific trust.
     * @param slot The slot entity to add.
     * @param trustId The ID of the trust.
     * @return The added Slot entity.
     */
    public Slot createSlot(Slot slot, HttpServletRequest request) {
        // Step 1: Extract JWT token from the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header.");
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Step 2: Extract email from the JWT token
        String email = jwtService.extractUserName(token); // Assuming extractUsername method fetches email

        // Step 3: Fetch the user by email
        Trust trust = trustRepository.findByEmail(email);
        
        System.out.println("create slot" + trust);
              

        // Step 4: Fetch the Trust entity using Trust ID from the user
        Long trustId = trust.getId();// Assuming user has a Trust association
       

        // Step 5: Associate the Slot with the Trust
        slot.setTrust(trust);

        // Step 6: Add the Slot to Trust's list of slots
        List<Slot> slots = trust.getSlots();
        if (slots == null) {
            slots = new ArrayList<>();
            trust.setSlots(slots);
        }
        slots.add(slot); // Adding the Slot to Trust's slots list

        // Step 7: Save the Slot and update Trust
        slot = slotRepository.save(slot);
        trustRepository.save(trust); // Update Trust to ensure it has the latest Slot list

        return slot;
    }

    /**
     * Updates an existing slot by ID.
     * @param id The ID of the slot to update.
     * @param slot The new slot details.
     * @return The updated Slot entity.
     */
    public Slot updateSlot(Long id, Slot slot) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        existingSlot.setName(slot.getName());
        existingSlot.setDateTime(slot.getDateTime());
        existingSlot.setAvailable(slot.isAvailable());
        return slotRepository.save(existingSlot);
    }

    /**
     * Deletes a slot by ID.
     * @param id The ID of the slot to delete.
     */
    public void deleteSlot(Long id) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        slotRepository.delete(existingSlot);
    }

    /**
     * Retrieves a slot by its ID.
     * @param id The ID of the slot to retrieve.
     * @return The Slot entity.
     */
    public Slot getSlotById(Long id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
    }

    /**
     * Retrieves all slots.
     * @return A list of all Slot entities.
     */
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    /**
     * Retrieves all slots for a specific trust by trust ID.
     * @param trustId The ID of the trust.
     * @return A list of Slot entities.
     */
    public List<Slot> getSlotsByTrustId(Long trustId) {
        return slotRepository.findByTrustId(trustId);
    }

    /**
     * Retrieves all available slots for a specific trust by trust ID.
     * @param trustId The ID of the trust.
     * @return A list of available Slot entities.
     */
    public List<Slot> getAvailableSlotsByTrustId(Long trustId) {
        return slotRepository.findByTrustIdAndAvailable(trustId, true);
    }

    /**
     * Retrieves all available slots for a specific trust and date.
     * @param trustId The ID of the trust.
     * @param date The date for which available slots are needed.
     * @return A list of available Slot entities.
     */
    public List<Slot> getAvailableSlotsForDateAndTrust(Long trustId, LocalDate date) {
        return slotRepository.findSlotsForDateAndTrust(trustId, date);
    }
}
