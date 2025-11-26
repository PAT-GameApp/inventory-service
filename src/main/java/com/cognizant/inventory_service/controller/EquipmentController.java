package com.cognizant.inventory_service.controller;

import com.cognizant.inventory_service.dto.EquipmentDTO;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<EquipmentDTO> getAllEquipment() {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        return equipmentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EquipmentDTO getEquipmentById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getEquipmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id " + id));
        return convertToDTO(equipment);
    }

    // ✅ Added @Valid for validation
    @PostMapping("/add")
    public EquipmentDTO addEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = convertToEntity(equipmentDTO);
        Equipment savedEquipment = equipmentService.addEquipment(equipment);
        return convertToDTO(savedEquipment);
    }

    // ✅ Added @Valid for validation
    @PutMapping("/{id}")
    public EquipmentDTO updateEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentDTO equipmentDTO) {
        Equipment updatedEntity = convertToEntity(equipmentDTO);
        Equipment updatedEquipment = equipmentService.updateEquipment(id, updatedEntity);
        return convertToDTO(updatedEquipment);
    }

    // ✅ Delete endpoint returns success message
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Long id) {
        String message = equipmentService.deleteEquipment(id);
        return ResponseEntity.ok(message);
    }

    // --- Conversion Methods ---
    private EquipmentDTO convertToDTO(Equipment equipment) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setEquipment_id(equipment.getEquipment_id());
        dto.setEquipment_name(equipment.getEquipment_name());
        dto.setEquipment_quantity(equipment.getEquipment_quantity());
        dto.setGame_id(equipment.getGame_id());
        dto.setCreated_at(equipment.getCreated_at());
        dto.setModified_at(equipment.getModified_at());
        return dto;
    }

    private Equipment convertToEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setEquipment_id(dto.getEquipment_id());
        equipment.setEquipment_name(dto.getEquipment_name());
        equipment.setEquipment_quantity(dto.getEquipment_quantity());
        equipment.setGame_id(dto.getGame_id());
        return equipment;
    }
}