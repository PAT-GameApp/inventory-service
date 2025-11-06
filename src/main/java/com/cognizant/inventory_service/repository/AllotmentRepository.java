package com.cognizant.inventory_service.repository;
 
import com.cognizant.inventory_service.entity.Allotment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface AllotmentRepository extends JpaRepository<Allotment, Long> {
}