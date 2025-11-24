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
@RequestMapping("/api/allotments")
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
        Equipment equipment = equipmentService.getEquipmentById(dto.getEquipment_id())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id " + dto.getEquipment_id()));

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
        dto.setAllotment_id((long) allotment.getAllotment_id());
        dto.setUser_id(allotment.getUser_id());
        dto.setCreated_at(allotment.getCreated_at());
        dto.setModified_at(allotment.getModified_at());
        dto.setEquipment_id(allotment.getEquipment().getEquipment_id());
        return dto;
    }

    private Allotment convertToEntity(AllotmentDTO dto, Equipment equipment) {
        Allotment allotment = new Allotment();
        if (dto.getAllotment_id() != null) {
            allotment.setAllotment_id(dto.getAllotment_id().intValue());
        }
        allotment.setUser_id(dto.getUser_id());
        allotment.setEquipment(equipment);
        allotment.setCreated_at(dto.getCreated_at() != null ? dto.getCreated_at() : LocalDateTime.now());
        allotment.setModified_at(LocalDateTime.now());
        return allotment;
    }
}