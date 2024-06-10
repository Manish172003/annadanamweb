package com.donation.annadanam.services;


import java.time.LocalDate;
import java.util.List;

import com.donation.annadanam.entities.Slot;


public interface SlotService {
    Slot addSlot(Slot slot);
    Slot updateSlot(Long id, Slot slot);
    void deleteSlot(Long id);
    Slot getSlotById(Long id);
    List<Slot> getAllSlots();
    List<Slot> getSlotsByTrustId(Long trustId);
    List<Slot> getAvailableSlotsByTrustId(Long trustId);
    Slot addSlotToTrust(Slot slot, Long trustId);
    List<Slot> getAvailableSlotsForDateAndTrust(Long trustId, LocalDate date);

}
