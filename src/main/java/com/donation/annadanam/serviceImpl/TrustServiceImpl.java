package com.donation.annadanam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;
import com.donation.annadanam.services.TrustService;

import java.util.List;

@Service
public class TrustServiceImpl implements TrustService {

    @Autowired
    private TrustRepository trustRepository;
    @Autowired
    private SlotServiceImpl slotRepository;
   
    @Override
    public Trust addTrust(Trust trust) {
        return trustRepository.save(trust);
    }
    
   
    
    @Override
    public Trust updateTrust(Long id, Trust trust) {
        Trust existingTrust = trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
        existingTrust.setName(trust.getName());
        return trustRepository.save(existingTrust);
    }

    @Override
    public void deleteTrust(Long id) {
        Trust existingTrust = trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
        trustRepository.delete(existingTrust);
    }

    @Override
    public Trust getTrustById(Long id) {
        return trustRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trust not found with id: " + id));
    }
    
    @Override
    public List<Slot> findAvailableSlots(Long trustId) {
        
        List<Slot> slots = slotRepository.getAvailableSlotsByTrustId(trustId);
        return slots;
    }

    @Override
    public List<Trust> getAllTrusts() {
        return trustRepository.findAll();
    }
}
