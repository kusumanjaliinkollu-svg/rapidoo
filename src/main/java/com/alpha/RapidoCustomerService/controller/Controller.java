package com.alpha.RapidoCustomerService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.RapidoCustomerService.Customerservice;
import com.alpha.RapidoCustomerService.dto.CustomerRideRequestDto;
import com.alpha.RapidoCustomerService.dto.Customerdto;
import com.alpha.RapidoCustomerService.dto.FareDto;
import com.alpha.RapidoCustomerService.dto.ResponseStructure;
import com.alpha.RapidoCustomerService.dto.SelectRideDto;
import com.alpha.RapidoCustomerService.entity.Customer;

@RestController
public class Controller {
	@Autowired
	private Customerservice customerservice;

	@PostMapping("/create/customer")
	public ResponseStructure<Customer> createcustomer(@RequestBody Customerdto crdto) {
		return customerservice.createcust(crdto);
	}
	
	@DeleteMapping("/delete/customer")
	public ResponseStructure<Customer> deletecust(@RequestParam int id) {
		return customerservice.deletecusto(id);
	}
	
	@GetMapping("/find/customer")
	public ResponseStructure<Customer> findcustomer(@RequestParam int id) {
		return customerservice.findcust(id);
	}
	
	@GetMapping("/customer/searchdroploc")
	public Object searchdroploc(@RequestParam String searchkey) {
		return  customerservice.SearchDropLoca(searchkey);
		
	}
	@GetMapping("/customer/selectrider")
	public FareDto selectRide(@RequestParam int cusid,@RequestBody SelectRideDto srdto) {
		return customerservice.selectrider(cusid,srdto);
		
	}
	
//	@PostMapping("customer/confirmride")
//	public ResponseStructure<String> confirmride(@RequestParam int cid,@RequestParam String vehc) {
//		return customerservice.confirride(cid,vehc);
//	}
	@PostMapping("/customer/confirmride")
	public ResponseStructure<String> confiride(@RequestParam int cid,@RequestParam String vehc){
		return customerservice.confirmride(cid,vehc);
	}
	
	@PostMapping("/customer/requestride")
	public List<String> requestRide( @RequestParam int cid,@RequestParam String vehc,@RequestBody CustomerRideRequestDto dto) {
	    return customerservice.requestRide(cid, vehc, dto);
	}
	 
}
