package com.donation.annadanam.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private TrustRepository trustRepository;

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
    public Slot addSlotToTrust(Slot slot, Long trustId) {
        Trust trust = trustRepository.findById(trustId)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + trustId));
        slot.setTrust(trust);
        return slotRepository.save(slot);
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
