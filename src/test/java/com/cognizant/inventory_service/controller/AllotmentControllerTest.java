package com.cognizant.inventory_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.cognizant.inventory_service.dto.AllotmentDTO;
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.service.AllotmentService;
import com.cognizant.inventory_service.service.EquipmentService;

@ExtendWith(MockitoExtension.class)
class AllotmentControllerTest {

    @Mock
    private AllotmentService allotmentService;

    @Mock
    private EquipmentService equipmentService;

    @InjectMocks
    private AllotmentController allotmentController;

    @Test
    void getAllAllotments_shouldReturnList() {
        Equipment e1 = new Equipment();
        e1.setEquipmentId(10L);
        Equipment e2 = new Equipment();
        e2.setEquipmentId(20L);

        Allotment a1 = new Allotment();
        a1.setAllotmentId(1L);
        a1.setEquipment(e1); // ensure non-null equipment

        Allotment a2 = new Allotment();
        a2.setAllotmentId(2L);
        a2.setEquipment(e2); // ensure non-null equipment

        when(allotmentService.getAllAllotments()).thenReturn(Arrays.asList(a1, a2));

        List<AllotmentDTO> result = allotmentController.getAllAllotments();

        assertEquals(2, result.size());
    }

    @Test
    void getAllotmentById_shouldReturnDTO() {
        Long id = 1L;
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(10L);

        Allotment allotment = new Allotment();
        allotment.setAllotmentId(id);
        allotment.setEquipment(equipment); // ensure non-null equipment

        when(allotmentService.getAllotmentById(id)).thenReturn(Optional.of(allotment));

        AllotmentDTO result = allotmentController.getAllotmentById(id);

        assertEquals(id, result.getAllotmentId());
    }

    @Test
    void addAllotment_shouldReturnCreatedDTO() {
        AllotmentDTO dto = new AllotmentDTO();
        dto.setEquipmentId(10L);
        dto.setUserId(20L);

        Equipment equipment = new Equipment();
        equipment.setEquipmentId(10L);

        Allotment saved = new Allotment();
        saved.setAllotmentId(1L);
        saved.setEquipment(equipment);
        saved.setUserId(20L);
        saved.setCreatedAt(LocalDateTime.now());
        saved.setModifiedAt(LocalDateTime.now());

        when(equipmentService.getEquipmentById(10L)).thenReturn(Optional.of(equipment));
        when(allotmentService.addAllotment(any(Allotment.class))).thenReturn(saved);

        AllotmentDTO result = allotmentController.addAllotment(dto);

        assertNotNull(result.getAllotmentId());
        assertEquals(10L, result.getEquipmentId());
        assertEquals(20L, result.getUserId());
    }

    @Test
    void deleteAllotment_shouldReturnOkAndMessage() {
        Long id = 1L;
        when(allotmentService.deleteAllotment(id)).thenReturn("The id " + id + " deleted successfully");

        ResponseEntity<String> response = allotmentController.deleteAllotment(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The id " + id + " deleted successfully", response.getBody());
    }

    @Test
    void returnAllotment_shouldReturnUpdatedDTO() {
        Long id = 1L;
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(10L);

        Allotment allotment = new Allotment();
        allotment.setAllotmentId(id);
        allotment.setEquipment(equipment); // ensure non-null equipment
        allotment.setReturned(true);

        when(allotmentService.returnAllotment(id)).thenReturn(allotment);

        ResponseEntity<AllotmentDTO> response = allotmentController.returnAllotment(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getAllotmentId());
        assertEquals(true, response.getBody().isReturned());
    }
}
