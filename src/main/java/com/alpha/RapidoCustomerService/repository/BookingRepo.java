package com.alpha.RapidoCustomerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.RapidoCustomerService.entity.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer>{

}
