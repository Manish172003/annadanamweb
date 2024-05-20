package com.donation.annadanam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.services.TrustService;

import java.util.List;

@RestController
@RequestMapping("/trusts")
public class TrustController {

    @Autowired
    private TrustService trustService;

    @PostMapping
    @RequestMapping("/addtrust")
    public ResponseEntity<Trust> addTrust(@RequestBody Trust trust) {
        Trust createdTrust = trustService.addTrust(trust);
        return ResponseEntity.ok(createdTrust);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trust> updateTrust(@PathVariable Long id, @RequestBody Trust trust) {
        Trust updatedTrust = trustService.updateTrust(id, trust);
        return ResponseEntity.ok(updatedTrust);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrust(@PathVariable Long id) {
        trustService.deleteTrust(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trust> getTrustById(@PathVariable Long id) {
        Trust trust = trustService.getTrustById(id);
        return ResponseEntity.ok(trust);
    }

    @GetMapping
    @RequestMapping("/getalltrusts")
    public ResponseEntity<List<Trust>> getAllTrusts() {
        List<Trust> trusts = trustService.getAllTrusts();
        return ResponseEntity.ok(trusts);
    }
}
