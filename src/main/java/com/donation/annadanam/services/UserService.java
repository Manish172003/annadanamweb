package com.donation.annadanam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.donation.annadanam.dao.DonarRepository;
import com.donation.annadanam.dao.TrustRepository;
import com.donation.annadanam.dao.UserRepository;
import com.donation.annadanam.dtos.AuthenticationRequest;
import com.donation.annadanam.dtos.AuthenticationResponse;
import com.donation.annadanam.dtos.DonarSignupRequest;
import com.donation.annadanam.dtos.TrustSignupRequest;
import com.donation.annadanam.entities.Users;
import com.donation.annadanam.entities.Donar;
import com.donation.annadanam.entities.Role;
import com.donation.annadanam.entities.Trust;


@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;
    
    @Autowired
    private TrustRepository trustrepo;
    
    @Autowired
    private DonarRepository donarrepo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    
//    @Override
//	public UserDto createCustomer(SignupRequest signupRequest) {
//		User user = new User();
//		user.setName(signupRequest.getName());
//		user.setEmail(signupRequest.getEmail());
//		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
//		user.setRole(Role.CUSTOMER);
//	    User createdUser = userRepository.save(user);
//	    
//	    UserDto createdUserDto = new UserDto();
//	    createdUserDto.setEmail(createdUser.getEmail());
//	    createdUserDto.setName(createdUser.getName());
//	    createdUserDto.setRole(createdUser.getRole());
//	    createdUserDto.setId(createdUser.getId());
//	    
//	    Customer customer = new Customer();
//	    customer.setName(createdUser.getName());
//	    customer.setId(createdUser.getId());
//	    customer.setEmail(createdUser.getEmail());
//		   System.out.println(createdUser.getId());
//		   customerRepository.save(customer);
//	    
//	    
//	 
//		return createdUserDto;
//	}

    public Trust registerTrust(TrustSignupRequest trustSignupRequest) {
    	
    	Users user = new Users();
    	
    	user.setRole(Role.TRUST);
        user.setPassword(encoder.encode(trustSignupRequest.getPassword()));
        user.setEmail(trustSignupRequest.getEmail());
        user.setUsername(trustSignupRequest.getUsername());
        
        
        Users createdUser = repo.save(user);
        
        Trust trust = new Trust();
        trust.setName(trustSignupRequest.getUsername());
        trust.setCity(trustSignupRequest.getCity());
        trustrepo.save(trust);
        return trust;
    }
    
    public Donar registerDonar(DonarSignupRequest donarSignupRequest) {
    	
        Users user = new Users();
    	user.setRole(Role.DONAR);
        user.setPassword(encoder.encode(donarSignupRequest.getPassword()));
        user.setEmail(donarSignupRequest.getEmail());
        user.setUsername(donarSignupRequest.getUsername());
        
        Users createdUser = repo.save(user);
        
        Donar donar = new Donar();
        donar.setEmail(donarSignupRequest.getEmail());
        donar.setName(donarSignupRequest.getName());
        donarrepo.save(donar);
        
        
        
        return donar;
       
    }


    public AuthenticationResponse verify(AuthenticationRequest user) {
  
    	
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
        	AuthenticationResponse response = new AuthenticationResponse();
            String token =  jwtService.generateToken(user.getEmail());
            response.setJwt(token);
            
            response.setRole(String.valueOf(authentication.getAuthorities()));
            
            return response;
        } 
        
        return null;
    }
}