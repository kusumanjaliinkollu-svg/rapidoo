package com.alpha.RapidoCustomerService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String idempotencyId;
	@ManyToOne
	private Customer customer;
	@OneToOne
	private Coordinates source;
	@OneToOne
	private Coordinates destination;
	private String destinationloc;
	private String pickuploc;
	private String paymenttype;
	private String vehicletype;
	private int riderid;
	private String bookingdate;
	private String bookingtime;
	private String pickuptime;
	private String droptime;
	private String fare;
	private String status;
	public Booking() {
		super();
	}
	
	public Booking(Customer customer, String pickuploc, String destinationloc, String paymenttype, String vehicletype,
			int riderid, String bookingdate, String bookingtime, String pickuptime, String droptime, String fare,Coordinates source,Coordinates destination,String status,String idempotencyId) {
		super();
		this.customer = customer;
		this.pickuploc = pickuploc;
		this.destinationloc = destinationloc;
		this.paymenttype = paymenttype;
		this.vehicletype = vehicletype;
		this.riderid = riderid;
		this.bookingdate = bookingdate;
		this.bookingtime = bookingtime;
		this.pickuptime = pickuptime;
		this.droptime = droptime;
		this.fare = fare;
		this.status=status;
		this.idempotencyId=idempotencyId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getPickuploc() {
		return pickuploc;
	}
	public void setPickuploc(String pickuploc) {
		this.pickuploc = pickuploc;
	}
	public String getDestinationloc() {
		return destinationloc;
	}
	public void setDestinationloc(String destinationloc) {
		this.destinationloc = destinationloc;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public int getRiderid() {
		return riderid;
	}
	public void setRiderid(int riderid) {
		this.riderid = riderid;
	}

	public String getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}

	public String getBookingtime() {
		return bookingtime;
	}

	public void setBookingtime(String bookingtime) {
		this.bookingtime = bookingtime;
	}

	public String getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(String pickuptime) {
		this.pickuptime = pickuptime;
	}

	public String getDroptime() {
		return droptime;
	}

	public void setDroptime(String droptime) {
		this.droptime = droptime;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public Coordinates getSource() {
		return source;
	}

	public void setSource(Coordinates source) {
		this.source = source;
	}

	public Coordinates getDestination() {
		return destination;
	}

	public void setDestination(Coordinates destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdempotencyId() {
		return idempotencyId;
	}

	public void setIdempotencyId(String idempotencyId) {
		this.idempotencyId = idempotencyId;
	}
	
	
}
