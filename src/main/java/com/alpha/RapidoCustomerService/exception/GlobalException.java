package com.alpha.RapidoCustomerService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.RapidoCustomerService.dto.ResponseStructure;
import com.alpha.RapidoCustomerService.entity.Address;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(AddressNotFound.class)
	public ResponseStructure<String> addressnotfound() {
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.NOT_FOUND.value());
		rs.setMessage("address Not Foound");
		rs.setData(null);
		return rs;
		
		
	}
	
	@ExceptionHandler(BookingNotFound.class)
	public ResponseStructure<String> bookingnotfound() {
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.NOT_FOUND.value());
		rs.setMessage("booking Not Foound");
		rs.setData(null);
		return rs;
		
		
	}
	
	@ExceptionHandler(CustomerNotFound.class)
	public ResponseStructure<String> customernotfound() {
		ResponseStructure<String> rs=new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.NOT_FOUND.value());
		rs.setMessage("customer Not Foound");
		rs.setData(null);
		return rs;
		
		
	}
}
