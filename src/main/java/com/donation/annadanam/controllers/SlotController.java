package com.donation.annadanam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.services.SlotService;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @PostMapping
    @RequestMapping("/addslot")
    public ResponseEntity<Slot> addSlot(@RequestBody Slot slot ) {
        Slot createdSlot = slotService.addSlot(slot);
        return ResponseEntity.ok(createdSlot);
    }
    
    @PostMapping
    @RequestMapping("/addslottotrust")
    public ResponseEntity<Slot> addSlotToTrust(@RequestBody Slot slot,@RequestParam Long trustId) {
    	Slot createdSlot = slotService.addSlotToTrust(slot, trustId);
        return ResponseEntity.ok(createdSlot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable Long id, @RequestBody Slot slot) {
        Slot updatedSlot = slotService.updateSlot(id, slot);
        return ResponseEntity.ok(updatedSlot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slot> getSlotById(@PathVariable Long id) {
        Slot slot = slotService.getSlotById(id);
        return ResponseEntity.ok(slot);
    }

    @GetMapping
    public ResponseEntity<List<Slot>> getAllSlots() {
        List<Slot> slots = slotService.getAllSlots();
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/trust/{trustId}")
    public ResponseEntity<List<Slot>> getSlotsByTrustId(@PathVariable Long trustId) {
        List<Slot> slots = slotService.getSlotsByTrustId(trustId);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/available/trust/{trustId}")
    public ResponseEntity<List<Slot>> getAvailableSlotsByTrustId(@PathVariable Long trustId) {
        List<Slot> slots = slotService.getAvailableSlotsByTrustId(trustId);
        return ResponseEntity.ok(slots);
    }
}
