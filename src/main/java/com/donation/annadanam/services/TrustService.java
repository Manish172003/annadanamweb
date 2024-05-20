package com.donation.annadanam.services;


import java.util.List;

import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;

public interface TrustService {
    Trust addTrust(Trust trust);
    Trust updateTrust(Long id, Trust trust);
    void deleteTrust(Long id);
    Trust getTrustById(Long id);
    List<Trust> getAllTrusts();
}
