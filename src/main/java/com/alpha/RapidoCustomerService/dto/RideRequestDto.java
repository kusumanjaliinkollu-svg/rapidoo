package com.alpha.RapidoCustomerService.dto;

public class RideRequestDto {
	private int rideId;
	private int customerId;
    private String vehicleType;

    private double pickupLatitude;
    private double pickupLongitude;

    private double dropLatitude;
    private double dropLongitude;
	public RideRequestDto(int rideId, int customerId, String vehicleType, double pickupLatitude, double pickupLongitude,
			double dropLatitude, double dropLongitude) {
		super();
		this.rideId=rideId;
		this.customerId = customerId;
		this.vehicleType = vehicleType;
		this.pickupLatitude = pickupLatitude;
		this.pickupLongitude = pickupLongitude;
		this.dropLatitude = dropLatitude;
		this.dropLongitude = dropLongitude;
	}
	public RideRequestDto() {
		super();
	}
	public int getCustomerId() {
		return customerId;
	}
	public int getRideId() {
		return rideId;
	}
	public void setRideId(int rideId) {
		this.rideId = rideId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
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
