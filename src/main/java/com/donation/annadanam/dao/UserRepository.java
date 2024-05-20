package com.donation.annadanam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.annadanam.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
