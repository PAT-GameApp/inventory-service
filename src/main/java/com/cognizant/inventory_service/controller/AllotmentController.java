package com.cognizant.inventory_service.controller;

import com.cognizant.inventory_service.dto.AllotmentDTO;
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.service.AllotmentService;
import com.cognizant.inventory_service.service.EquipmentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/allotments")
public class AllotmentController {

    @Autowired
    private AllotmentService allotmentService;

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public List<AllotmentDTO> getAllAllotments() {
        return allotmentService.getAllAllotments()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AllotmentDTO getAllotmentById(@PathVariable Integer id) {
        Allotment allotment = allotmentService.getAllotmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allotment not found with id " + id));
        return convertToDTO(allotment);
    }

    @PostMapping("/addAllotment")
    public AllotmentDTO addAllotment(@Valid @RequestBody AllotmentDTO dto) {
        Equipment equipment = equipmentService.getEquipmentById(dto.getEquipmentId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Equipment not found with id " + dto.getEquipmentId()));

        Allotment allotment = convertToEntity(dto, equipment);
        Allotment saved = allotmentService.addAllotment(allotment);
        return convertToDTO(saved);
    }

    // ✅ Updated delete endpoint to return success message
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllotment(@PathVariable Integer id) {
        String message = allotmentService.deleteAllotment(id);
        return ResponseEntity.ok(message);
    }

    // --- Conversion Methods ---
    private AllotmentDTO convertToDTO(Allotment allotment) {
        AllotmentDTO dto = new AllotmentDTO();
        dto.setAllotmentId((long) allotment.getAllotmentId());
        dto.setUserId(allotment.getUserId());
        dto.setCreatedAt(allotment.getCreatedAt());
        dto.setModifiedAt(allotment.getModifiedAt());
        dto.setEquipmentId(allotment.getEquipment().getEquipmentId());
        return dto;
    }

    private Allotment convertToEntity(AllotmentDTO dto, Equipment equipment) {
        Allotment allotment = new Allotment();
        if (dto.getAllotmentId() != null) {
            allotment.setAllotmentId(dto.getAllotmentId().intValue());
        }
        allotment.setUserId(dto.getUserId());
        allotment.setEquipment(equipment);
        allotment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        allotment.setModifiedAt(LocalDateTime.now());
        return allotment;
    }
}