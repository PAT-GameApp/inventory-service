package com.cognizant.inventory_service.repository;
 
import com.cognizant.inventory_service.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}