package com.alpha.RapidoCustomerService.dto;

public class CustomerRideRequestDto {
	 private double pickupLatitude;
	 private double pickupLongitude;

	 private double dropLatitude;
	 private double dropLongitude;
	 public double getPickupLatitude() {
		 return pickupLatitude;
	 }
	 public void setPickupLatitude(double pickupLatitude) {
		 this.pickupLatitude = pickupLatitude;
	 }
	 public double getPickupLongitude() {
		 return pickupLongitude;
	 }
	 public void setPickupLongitude(double pickupLongitude) {
		 this.pickupLongitude = pickupLongitude;
	 }
	 public double getDropLatitude() {
		 return dropLatitude;
	 }
	 public void setDropLatitude(double dropLatitude) {
		 this.dropLatitude = dropLatitude;
	 }
	 public double getDropLongitude() {
		 return dropLongitude;
	 }
	 public void setDropLongitude(double dropLongitude) {
		 this.dropLongitude = dropLongitude;
	 }
	 
	 
	 
}
