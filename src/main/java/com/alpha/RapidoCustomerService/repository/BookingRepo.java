package com.alpha.RapidoCustomerService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.RapidoCustomerService.entity.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer>{
	 Optional<Booking> findByIdempotencyId(String idempotencyId);
	 List<Booking> findByCustomerIdAndStatus(int cid, String status);
	 List<Booking> findByCustomerId(int cid);
}
