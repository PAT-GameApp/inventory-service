package com.cognizant.inventory_service.service;

import com.cognizant.inventory_service.dto.EquipmentAvailableResponseDTO;
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.repository.EquipmentRepository;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Equipment> getAllEquipment(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    public List<Equipment> getAllEquipment(Sort sort) {
        return sort == null || sort.isUnsorted() ? equipmentRepository.findAll() : equipmentRepository.findAll(sort);
    }

    public List<Equipment> getEquipmentByGameId(Long gameId) {
        return equipmentRepository.findByGameId(gameId);
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
                    existing.setEquipmentName(updatedEquipment.getEquipmentName());
                    existing.setEquipmentQuantity(updatedEquipment.getEquipmentQuantity());
                    existing.setGameId(updatedEquipment.getGameId());
                    return equipmentRepository.save(existing);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id " + id));
    }

    // Updated delete method
    public String deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("The id " + id + " does not exist");
        }
        equipmentRepository.deleteById(id);
        return "The id " + id + " deleted successfully";
    }

    public EquipmentAvailableResponseDTO getEquipmentAvailability(Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id: "));
        List<Allotment> activeAllotments = equipmentRepository.findActiveAllotments(id);
        int availableQuantity = equipment.getEquipmentQuantity() - activeAllotments.size();

        return EquipmentAvailableResponseDTO
                .builder()
                .availableQuantity(availableQuantity)
                .build();
    }

}
