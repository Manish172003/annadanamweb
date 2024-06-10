package com.donation.annadanam.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
	 List<Slot> findByTrustId(Long trustId);
	 List<Slot> findByTrustIdAndAvailable(Long trustId, boolean available);
	 @Query("SELECT s FROM Slot s WHERE s.trust.id = :trustId AND DATE(s.dateTime) = :date")
	 List<Slot> findSlotsForDateAndTrust(@Param("trustId") Long trustId, @Param("date") LocalDate date);

}

