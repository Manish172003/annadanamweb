package com.donation.annadanam.controllers;

import java.io.IOException;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.donation.annadanam.dtos.AuthenticationRequest;
import com.donation.annadanam.dtos.AuthenticationResponse;
import com.donation.annadanam.dtos.DonarSignupRequest;
import com.donation.annadanam.dtos.TrustSignupRequest;
import com.donation.annadanam.entities.Donar;
import com.donation.annadanam.entities.Trust;
import com.donation.annadanam.entities.Users;
import com.donation.annadanam.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;	

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000/")
public class AuthController {
	
	

	private UserService  authService;
	public AuthController(UserService service) {
		this.authService = service;
	}
//   

	
	@PostMapping("/trust/signup")
	public ResponseEntity<?> trustsignup(@RequestBody TrustSignupRequest trustDetails)
	{
		Trust user = authService.registerTrust(trustDetails);
		if(user == null)
		{
			return new ResponseEntity<>("Trust not Created", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	// userDetails is just a variable name , do not confuse..
	@PostMapping("/donar/signup")
	public ResponseEntity<?> donarsignup(@RequestBody DonarSignupRequest donarDetails)
	{
		 Donar user = authService.registerDonar(donarDetails);
		if(user == null)
		{
			return new ResponseEntity<>("Donar not Created", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse  response =  authService.verify(authenticationRequest);
        if(response == null)
        {
        	return new ResponseEntity<>("Login Failed", HttpStatus.BAD_REQUEST);
        }
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	
	

	
//	@PostMapping("/login")
//	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpSession session) throws IOException
//	{
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
//		} catch (BadCredentialsException e) {
//			throw new BadCredentialsException("Incorrect UserName or Password");
//		}catch(DisabledException disabledException)
//		{
//			response.sendError(HttpServletResponse.SC_NOT_FOUND,"User is Not Active");
//			return null;
//		}
//		
//		final UserDetails userDetails = userDetailsService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
//		final String jwt = Jwtutil.generateToken(userDetails);
//		Optional<com.example.demo.entities.User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
//	    
//	  
//		
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//		
//		if(optionalUser.isPresent())
//		{
//			session.setAttribute("restaurantid", optionalUser.get().getId());
//			authenticationResponse.setJwt(jwt);
//			authenticationResponse.setRole(optionalUser.get().getRole());
//			authenticationResponse.setUserid(optionalUser.get().getId());
//			authenticationResponse.setUserName(optionalUser.get().getName());
//		}
//		return  authenticationResponse;
//		
//	}
	
	
	

}
