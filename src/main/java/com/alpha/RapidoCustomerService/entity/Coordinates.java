package com.alpha.RapidoCustomerService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coordinates {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
