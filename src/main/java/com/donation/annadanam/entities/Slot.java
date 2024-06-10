	package com.donation.annadanam.entities;
	
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	
	import java.time.LocalDateTime;
	
	import com.fasterxml.jackson.annotation.JsonBackReference;
	import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
	import com.fasterxml.jackson.annotation.JsonManagedReference;
	
	@Entity
	public class Slot {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    private String name; 
	    private LocalDateTime dateTime;
	    private boolean available;
	
	    @ManyToOne
	    @JoinColumn(name = "trust_id")
	    @JsonIgnoreProperties("slots")
	    private Trust trust;
	    
	    @ManyToOne
	    @JoinColumn(name = "booking_id")
	    @JsonIgnoreProperties("slots")
	    private Booking booking;
	
	    public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public LocalDateTime getDateTime() {
			return dateTime;
		}
	
		public void setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
		}
	
		public boolean isAvailable() {
			return available;
		}
	
		public void setAvailable(boolean available) {
			this.available = available;
		}
	
		public Trust getTrust() {
			return trust;
		}
	
		public void setTrust(Trust trust) {
			this.trust = trust;
		}
	
		public Booking getBooking() {
			return booking;
		}
	
		public void setBooking(Booking booking) {
			this.booking = booking;
		}
	
	
	   
	}
