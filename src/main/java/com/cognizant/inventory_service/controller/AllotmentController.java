package com.cognizant.inventory_service.controller;

import com.cognizant.inventory_service.dto.AllotmentDTO;
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.service.AllotmentService;
import com.cognizant.inventory_service.service.EquipmentService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

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

    @GetMapping("/")
    public List<AllotmentDTO> getAllAllotments() {
        return allotmentService.getAllAllotments()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AllotmentDTO getAllotmentById(@PathVariable Long id) {
        Allotment allotment = allotmentService.getAllotmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allotment not found with id " + id));
        return convertToDTO(allotment);
    }

    @PostMapping("/")
    public AllotmentDTO addAllotment(@Valid @RequestBody AllotmentDTO dto) {
        Equipment equipment = equipmentService.getEquipmentById(dto.getEquipmentId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Equipment not found with id " + dto.getEquipmentId()));

        Allotment allotment = convertToEntity(dto, equipment);
        Allotment saved = allotmentService.addAllotment(allotment);
        return convertToDTO(saved);
    }

    // Updated delete endpoint to return success message
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllotment(@PathVariable Long id) {
        String message = allotmentService.deleteAllotment(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<AllotmentDTO> returnAllotment(@PathVariable Long id) {
        Allotment allotment = allotmentService.returnAllotment(id);
        return ResponseEntity.ok(convertToDTO(allotment));
    }

    // --- Conversion Methods ---
    private AllotmentDTO convertToDTO(Allotment allotment) {
        AllotmentDTO dto = new AllotmentDTO();
        dto.setAllotmentId((long) allotment.getAllotmentId());
        dto.setUserId(allotment.getUserId());
        dto.setCreatedAt(allotment.getCreatedAt());
        dto.setModifiedAt(allotment.getModifiedAt());
        dto.setEquipmentId(allotment.getEquipment().getEquipmentId());
        dto.setReturned(allotment.isReturned());
        dto.setBookingId(allotment.getBookingId());
        return dto;
    }

    private Allotment convertToEntity(AllotmentDTO dto, Equipment equipment) {
        Allotment allotment = new Allotment();
        if (dto.getAllotmentId() != null) {
            allotment.setAllotmentId(dto.getAllotmentId().longValue());
        }
        allotment.setUserId(dto.getUserId());
        allotment.setEquipment(equipment);
        allotment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        allotment.setModifiedAt(LocalDateTime.now());
        return allotment;
    }
}
