package com.donation.annadanam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
	 List<Slot> findByTrustId(Long trustId);
	 List<Slot> findByTrustIdAndAvailable(Long trustId, boolean available);
}

