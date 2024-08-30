package com.donation.annadanam.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class TrustService {

    @Autowired
    private TrustRepository trustRepository;

    @Autowired
    private SlotRepository slotRepository;

    /**
     * Adds a new trust to the database.
     * @param trust The Trust entity to add.
     * @return The added Trust entity.
     */
    public Trust addTrust(Trust trust) {
        return trustRepository.save(trust);
    }

    /**
     * Updates an existing trust by ID.
     * @param id The ID of the trust to update.
     * @param trust The new Trust details.
     * @return The updated Trust entity.
     */
    public Trust updateTrust(Long id, Trust trust) {
        Trust existingTrust = trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
        existingTrust.setName(trust.getName());
        return trustRepository.save(existingTrust);
    }

    /**
     * Deletes a trust by ID.
     * @param id The ID of the trust to delete.
     */
    public void deleteTrust(Long id) {
        Trust existingTrust = trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
        trustRepository.delete(existingTrust);
    }

    /**
     * Retrieves a trust by its ID.
     * @param id The ID of the trust to retrieve.
     * @return The Trust entity.
     */
    public Trust getTrustById(Long id) {
        return trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
    }

    /**
     * Retrieves all trusts.
     * @return A list of all Trust entities.
     */
    public List<Trust> getAllTrusts() {
        return trustRepository.findAll();
    }

    /**
     * Finds available slots for a specific trust.
     * @param trustId The ID of the trust.
     * @return A list of available Slot entities.
     */
    public List<Slot> findAvailableSlots(Long trustId) {
        return slotRepository.findByTrustIdAndAvailable(trustId, true);
    }

    /**
     * Retrieves all trusts by city.
     * @param city The city to filter trusts by.
     * @return A list of Trust entities in the specified city.
     */
    public List<Trust> getTrustsByCity(String city) {
        return trustRepository.findByCity(city);
    }
}
