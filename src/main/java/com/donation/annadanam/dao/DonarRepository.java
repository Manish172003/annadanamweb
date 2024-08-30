package com.donation.annadanam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.Donar;

@Repository
public interface DonarRepository extends JpaRepository<Donar, Long>{

}
