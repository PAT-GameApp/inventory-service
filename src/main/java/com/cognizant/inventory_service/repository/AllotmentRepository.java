package com.cognizant.inventory_service.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.inventory_service.entity.Allotment;
 
@Repository
public interface AllotmentRepository extends JpaRepository<Allotment, Integer> {
	
}