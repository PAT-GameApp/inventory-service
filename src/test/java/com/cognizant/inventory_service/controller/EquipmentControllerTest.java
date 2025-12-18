package com.cognizant.inventory_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.inventory_service.dto.EquipmentAvailableResponseDTO;
import com.cognizant.inventory_service.dto.EquipmentDTO;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.service.EquipmentService;

@ExtendWith(MockitoExtension.class)
class EquipmentControllerTest {

    @Mock
    private EquipmentService equipmentService;

    @InjectMocks
    private EquipmentController equipmentController;

    @Test
    void getAllEquipment_shouldReturnList() {
        Equipment e1 = new Equipment();
        e1.setEquipmentId(1L);
        e1.setEquipmentName("Bat");
        Equipment e2 = new Equipment();
        e2.setEquipmentId(2L);
        e2.setEquipmentName("Ball");

        when(equipmentService.getAllEquipment()).thenReturn(Arrays.asList(e1, e2));

        List<EquipmentDTO> result = equipmentController.getAllEquipment();

        assertEquals(2, result.size());
        assertEquals("Bat", result.get(0).getEquipmentName());
    }

    @Test
    void getEquipmentById_shouldReturnDTO() {
        Long id = 1L;
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(id);
        equipment.setEquipmentName("Bat");

        when(equipmentService.getEquipmentById(id)).thenReturn(Optional.of(equipment));

        EquipmentDTO result = equipmentController.getEquipmentById(id);

        assertEquals(id, result.getEquipmentId());
        assertEquals("Bat", result.getEquipmentName());
    }

    @Test
    void addEquipment_shouldReturnCreatedDTO() {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setEquipmentName("Bat");
        dto.setEquipmentQuantity(5);
        dto.setGameId(10L);

        Equipment saved = new Equipment();
        saved.setEquipmentId(1L);
        saved.setEquipmentName("Bat");
        saved.setEquipmentQuantity(5);
        saved.setGameId(10L);

        when(equipmentService.addEquipment(any(Equipment.class))).thenReturn(saved);

        EquipmentDTO result = equipmentController.addEquipment(dto);

        assertNotNull(result.getEquipmentId());
        assertEquals("Bat", result.getEquipmentName());
    }

    @Test
    void updateEquipment_shouldReturnUpdatedDTO() {
        Long id = 1L;
        EquipmentDTO dto = new EquipmentDTO();
        dto.setEquipmentName("Updated Bat");
        dto.setEquipmentQuantity(7);
        dto.setGameId(20L);

        Equipment updated = new Equipment();
        updated.setEquipmentId(id);
        updated.setEquipmentName("Updated Bat");
        updated.setEquipmentQuantity(7);
        updated.setGameId(20L);

        when(equipmentService.updateEquipment(any(Long.class), any(Equipment.class))).thenReturn(updated);

        EquipmentDTO result = equipmentController.updateEquipment(id, dto);

        assertEquals(id, result.getEquipmentId());
        assertEquals("Updated Bat", result.getEquipmentName());
    }

    @Test
    void deleteEquipment_shouldReturnOkAndMessage() {
        Long id = 1L;
        when(equipmentService.deleteEquipment(id)).thenReturn("The id " + id + " deleted successfully");

        ResponseEntity<String> response = equipmentController.deleteEquipment(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The id " + id + " deleted successfully", response.getBody());
    }

    @Test
    void getAvailableQuantity_shouldReturnOkAndBody() {
        Long id = 1L;
        EquipmentAvailableResponseDTO dto = EquipmentAvailableResponseDTO.builder()
                .availableQuantity(3)
                .build();

        when(equipmentService.getEquipmentAvailability(id)).thenReturn(dto);

        ResponseEntity<?> response = equipmentController.getAvailableQuantity(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, ((EquipmentAvailableResponseDTO) response.getBody()).getAvailableQuantity());
    }
}
