package com.donation.annadanam.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.SlotRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.entities.Booking;
import com.donation.annadanam.entities.Donar;
import com.donation.annadanam.entities.Slot;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonarService {

    @Autowired
    private TrustRepository trustRepository;

    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private JWTService jwtService;

   
    


}
