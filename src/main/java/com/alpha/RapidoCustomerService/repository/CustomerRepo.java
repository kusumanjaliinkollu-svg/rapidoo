package com.alpha.RapidoCustomerService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.RapidoCustomerService.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	public Optional<Customer> findByMobile(long mobile);

}
