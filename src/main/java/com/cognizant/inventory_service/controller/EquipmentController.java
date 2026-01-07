package com.cognizant.inventory_service.controller;

import com.cognizant.inventory_service.dto.EquipmentAvailableResponseDTO;
import com.cognizant.inventory_service.dto.EquipmentDTO;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.inventory_service.util.PagingUtil;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/")
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction) {
        List<String> allowedSort = Arrays.asList("equipmentId", "equipmentName", "equipmentQuantity", "gameId", "createdAt", "modifiedAt");
        Pageable pageable = PagingUtil.buildPageable(page, size, sort, direction, allowedSort);
        if (pageable != null) {
            Page<Equipment> p = equipmentService.getAllEquipment(pageable);
            HttpHeaders headers = PagingUtil.buildHeaders(p);
            List<EquipmentDTO> body = p.getContent().stream().map(this::convertToDTO).collect(Collectors.toList());
            return ResponseEntity.ok().headers(headers).body(body);
        }
        Sort sortSpec = PagingUtil.buildSort(sort, direction, allowedSort);
        List<Equipment> list = equipmentService.getAllEquipment(sortSpec);
        List<EquipmentDTO> body = list.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public EquipmentDTO getEquipmentById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getEquipmentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found with id " + id));
        return convertToDTO(equipment);
    }

    // Added @Valid for validation
    @PostMapping("/add")
    public EquipmentDTO addEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = convertToEntity(equipmentDTO);
        Equipment savedEquipment = equipmentService.addEquipment(equipment);
        return convertToDTO(savedEquipment);
    }

    // Added @Valid for validation
    @PutMapping("/{id}")
    public EquipmentDTO updateEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentDTO equipmentDTO) {
        Equipment updatedEntity = convertToEntity(equipmentDTO);
        Equipment updatedEquipment = equipmentService.updateEquipment(id, updatedEntity);
        return convertToDTO(updatedEquipment);
    }

    // Delete endpoint returns success message
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Long id) {
        String message = equipmentService.deleteEquipment(id);
        return ResponseEntity.ok(message);
    }

    // method to check available equipment quantity
    @GetMapping("/{id}/available")
    public ResponseEntity<?> getAvailableQuantity(@PathVariable Long id) {
        EquipmentAvailableResponseDTO response = equipmentService.getEquipmentAvailability(id);
        return ResponseEntity.ok(response);
    }

    // --- Conversion Methods ---
    private EquipmentDTO convertToDTO(Equipment equipment) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setEquipmentId(equipment.getEquipmentId());
        dto.setEquipmentName(equipment.getEquipmentName());
        dto.setEquipmentQuantity(equipment.getEquipmentQuantity());
        dto.setGameId(equipment.getGameId());
        dto.setCreatedAt(equipment.getCreatedAt());
        dto.setModifiedAt(equipment.getModifiedAt());
        return dto;
    }

    private Equipment convertToEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(dto.getEquipmentId());
        equipment.setEquipmentName(dto.getEquipmentName());
        equipment.setEquipmentQuantity(dto.getEquipmentQuantity());
        equipment.setGameId(dto.getGameId());
        return equipment;
    }
}
