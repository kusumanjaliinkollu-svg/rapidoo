package com.alpha.RapidoCustomerService.dto;

import com.alpha.RapidoCustomerService.entity.Address;
import com.alpha.RapidoCustomerService.entity.Customer;

public class Bookingdto {		
		private Customer customer;
		private Address pickuploc;
		private Address destinationloc;
		private String paymenttype;
		private String vehicletype;
		private int riderid;
		private String bookingdate;
		private String bookingtime;
		private String pickuptime;
		private String droptime;
		private String fare;
		public Bookingdto() {
			super();
		}
		
		public Bookingdto(Customer customer, Address pickuploc, Address destinationloc, String paymenttype, String vehicletype,
				int riderid, String bookingdate, String bookingtime, String pickuptime, String droptime, String fare) {
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
		}

		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public Address getPickuploc() {
			return pickuploc;
		}
		public void setPickuploc(Address pickuploc) {
			this.pickuploc = pickuploc;
		}
		public Address getDestinationloc() {
			return destinationloc;
		}
		public void setDestinationloc(Address destinationloc) {
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
		
		
	}


