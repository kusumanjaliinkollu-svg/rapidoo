package com.alpha.RapidoCustomerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.RapidoCustomerService.entity.Coordinates;

@Repository
public interface CoordinateRepo extends JpaRepository<Coordinates, Integer>{

}
