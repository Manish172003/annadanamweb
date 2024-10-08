package com.donation.annadanam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Trust;

@Repository
public interface TrustRepository extends JpaRepository<Trust, Long> {
	List<Trust> findByCity(String city);
	Trust findByEmail(String email);
}
