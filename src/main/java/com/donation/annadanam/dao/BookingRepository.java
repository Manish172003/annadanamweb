package com.donation.annadanam.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByTrustId(Long trustid);
	List<Booking> findByDonarId(Long donarid);
}
