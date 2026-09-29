package com.alpha.RapidoCustomerService.dto;

import com.alpha.RapidoCustomerService.entity.Coordinates;

public class SelectRideDto {
	private Coordinates sourceCordinate;
	private Coordinates destinationCordinate;
	public SelectRideDto(Coordinates sourceCordinate, Coordinates destinationCordinate) {
		super();
		this.sourceCordinate = sourceCordinate;
		this.destinationCordinate = destinationCordinate;
	}
	public SelectRideDto() {
		super();
	}
	public Coordinates getSourceCordinate() {
		return sourceCordinate;
	}
	public void setSourceCordinate(Coordinates sourceCordinate) {
		this.sourceCordinate = sourceCordinate;
	}
	public Coordinates getDestinationCordinate() {
		return destinationCordinate;
	}
	public void setDestinationCordinate(Coordinates destinationCordinate) {
		this.destinationCordinate = destinationCordinate;
	}
	
	

}
