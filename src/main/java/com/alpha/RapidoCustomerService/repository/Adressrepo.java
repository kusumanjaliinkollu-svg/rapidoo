package com.alpha.RapidoCustomerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.RapidoCustomerService.entity.Address;

@Repository
public interface Adressrepo extends JpaRepository<Address, Integer> {
	

}
