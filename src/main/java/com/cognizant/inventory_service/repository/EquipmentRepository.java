package com.cognizant.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("""
                SELECT a
                FROM Equipment e
                JOIN e.allotments a
                WHERE e.equipmentId = :id
                  AND a.returned = false
            """)
    List<Allotment> findActiveAllotments(@Param("id") Long equipmentId);

}
