package com.donation.annadanam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Trust;

@Repository
public interface TrustRepository extends JpaRepository<Trust, Long> {
	
}
