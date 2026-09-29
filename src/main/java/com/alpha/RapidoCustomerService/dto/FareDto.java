package com.alpha.RapidoCustomerService.dto;

import java.util.Map;

public class FareDto {
      private String Source;
      private String destination;
    
      private Map<String, Double> fare;
	  public FareDto(String source, String destination, Map<String, Double> fare) {
		super();
		Source = source;
		this.destination = destination;
		
		this.fare = fare;
	  }
	  public FareDto() {
		super();
	  }
	  public String getSource() {
		  return Source;
	  }
	  public void setSource(String source) {
		  Source = source;
	  }
	  public String getDestination() {
		  return destination;
	  }
	  public void setDestination(String destination) {
		  this.destination = destination;
	  }
	  public Map<String, Double> getFare() {
		  return fare;
	  }
	  public void setFare(Map<String, Double> fare) {
		  this.fare = fare;
	  }
      
      
      
}
