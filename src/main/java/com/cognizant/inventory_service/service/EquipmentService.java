package com.cognizant.inventory_service.service;
 
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class EquipmentService {
 
    @Autowired
    private EquipmentRepository equipmentRepository;
 
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }
 
    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }
 
    public Equipment addEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }
 
    public Equipment updateEquipment(Long id, Equipment updatedEquipment) {
        return equipmentRepository.findById(id)
                .map(existing -> {
                    existing.setEquipment_name(updatedEquipment.getEquipment_name());
                    existing.setEquipment_quantity(updatedEquipment.getEquipment_quantity());
                    existing.setGame_id(updatedEquipment.getGame_id());
                    return equipmentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Equipment not found with id " + id));
    }
 
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
}
 