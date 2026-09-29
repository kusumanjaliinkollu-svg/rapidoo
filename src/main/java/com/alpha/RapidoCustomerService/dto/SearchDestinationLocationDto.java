package com.alpha.RapidoCustomerService.dto;

public class SearchDestinationLocationDto {
	
	private String display_name;
	private double lati;
	private double  longi;
	public SearchDestinationLocationDto(String display_name, double lati, double longi) {
		super();
		this.display_name = display_name;
		this.lati = lati;
		this.longi = longi;
	}
	public SearchDestinationLocationDto() {
		super();
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public double getLati() {
		return lati;
	}
	public void setLati(double lati) {
		this.lati = lati;
	}
	public double getLongi() {
		return longi;
	}
	public void setLongi(double longi) {
		this.longi = longi;
	}
	
	
	

}
