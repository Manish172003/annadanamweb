package com.donation.annadanam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;
import com.donation.annadanam.services.SlotService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private TrustRepository trustRepository;
   

    @Override
    public Slot addSlot(Slot slot) {
    	
        return slotRepository.save(slot);
    }
    @Override
    public Slot addSlotToTrust(Slot slot, Long trustId) {
        Trust trust = trustRepository.findById(trustId)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + trustId));
        slot.setTrust(trust);
        return slotRepository.save(slot);
    }

    @Override
    public Slot updateSlot(Long id, Slot slot) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        existingSlot.setName(slot.getName());
        existingSlot.setDateTime(slot.getDateTime());
        existingSlot.setAvailable(slot.isAvailable());
        return slotRepository.save(existingSlot);
    }

    @Override
    public void deleteSlot(Long id) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        slotRepository.delete(existingSlot);
    }

    @Override
    public Slot getSlotById(Long id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
    }

    @Override
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }
    @Override
    public List<Slot> getSlotsByTrustId(Long trustId) {
        return slotRepository.findByTrustId(trustId);
    }

    @Override
    public List<Slot> getAvailableSlotsByTrustId(Long trustId) {
        return slotRepository.findByTrustIdAndAvailable(trustId, true);
    }
    
    @Override
    public List<Slot> getAvailableSlotsForDateAndTrust(Long trustId, LocalDate date) {
        return slotRepository.findSlotsForDateAndTrust(trustId, date);
    }
}
