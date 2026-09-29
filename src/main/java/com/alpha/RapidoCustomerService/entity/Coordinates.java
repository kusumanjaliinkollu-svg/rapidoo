package com.alpha.RapidoCustomerService.entity;
public class Coordinates {
	private double lati;
	private double longi;
	public Coordinates(double lati, double longi) {
		super();
		this.lati = lati;
		this.longi = longi;
	}
	public Coordinates() {
		super();
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
