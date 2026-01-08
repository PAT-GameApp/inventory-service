package com.cognizant.inventory_service.service;

import com.cognizant.inventory_service.dto.EquipmentAvailableResponseDTO;
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.entity.Equipment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.repository.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    private Equipment equipment;

    @BeforeEach
    void setUp() {
        equipment = Equipment.builder()
                .equipmentId(1L)
                .equipmentName("Bat")
                .equipmentQuantity(10)
                .gameId(100L)
                .build();
    }

    @Test
    void getAllEquipment_withoutSort_returnsAllFromRepository() {
        when(equipmentRepository.findAll()).thenReturn(List.of(equipment));

        List<Equipment> result = equipmentService.getAllEquipment();

        assertThat(result).containsExactly(equipment);
        verify(equipmentRepository).findAll();
    }

    @Test
    void getAllEquipment_withPageable_delegatesToRepository() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Equipment> page = new PageImpl<>(List.of(equipment), pageable, 1);
        when(equipmentRepository.findAll(pageable)).thenReturn(page);

        Page<Equipment> result = equipmentService.getAllEquipment(pageable);

        assertThat(result.getContent()).containsExactly(equipment);
        verify(equipmentRepository).findAll(pageable);
    }

    @Test
    void getAllEquipment_withSorted_returnsSortedFromRepository() {
        Sort sort = Sort.by("equipmentName");
        when(equipmentRepository.findAll(sort)).thenReturn(List.of(equipment));

        List<Equipment> result = equipmentService.getAllEquipment(sort);

        assertThat(result).containsExactly(equipment);
        verify(equipmentRepository).findAll(sort);
    }

    @Test
    void getAllEquipment_withUnsortedSort_fallsBackToFindAll() {
        Sort sort = Sort.unsorted();
        when(equipmentRepository.findAll()).thenReturn(List.of(equipment));

        List<Equipment> result = equipmentService.getAllEquipment(sort);

        assertThat(result).containsExactly(equipment);
        verify(equipmentRepository).findAll();
        verify(equipmentRepository, never()).findAll(sort);
    }

    @Test
    void getEquipmentById_returnsOptionalFromRepository() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));

        Optional<Equipment> result = equipmentService.getEquipmentById(1L);

        assertThat(result).contains(equipment);
        verify(equipmentRepository).findById(1L);
    }

    @Test
    void addEquipment_savesAndReturnsEntity() {
        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        Equipment result = equipmentService.addEquipment(equipment);

        assertThat(result).isEqualTo(equipment);
        verify(equipmentRepository).save(equipment);
    }

    @Test
    void updateEquipment_whenExisting_updatesAndReturnsEntity() {
        Equipment updated = Equipment.builder()
                .equipmentName("Ball")
                .equipmentQuantity(5)
                .gameId(200L)
                .build();

        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Equipment result = equipmentService.updateEquipment(1L, updated);

        assertEquals("Ball", result.getEquipmentName());
        assertEquals(5, result.getEquipmentQuantity());
        assertEquals(200L, result.getGameId());
        verify(equipmentRepository).findById(1L);
        verify(equipmentRepository).save(any(Equipment.class));
    }

    @Test
    void updateEquipment_whenNotFound_throwsResourceNotFound() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> equipmentService.updateEquipment(1L, equipment));

        verify(equipmentRepository).findById(1L);
        verify(equipmentRepository, never()).save(any());
    }

    @Test
    void deleteEquipment_whenIdExists_deletesAndReturnsMessage() {
        when(equipmentRepository.existsById(1L)).thenReturn(true);

        String message = equipmentService.deleteEquipment(1L);

        assertEquals("The id 1 deleted successfully", message);
        verify(equipmentRepository).existsById(1L);
        verify(equipmentRepository).deleteById(1L);
    }

    @Test
    void deleteEquipment_whenIdDoesNotExist_throwsResourceNotFound() {
        when(equipmentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> equipmentService.deleteEquipment(1L));

        verify(equipmentRepository).existsById(1L);
        verify(equipmentRepository, never()).deleteById(anyLong());
    }

    @Test
    void getEquipmentAvailability_whenEquipmentExists_calculatesAvailableQuantity() {
        Allotment a1 = new Allotment();
        Allotment a2 = new Allotment();
        List<Allotment> active = List.of(a1, a2);

        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.findActiveAllotments(1L)).thenReturn(active);

        EquipmentAvailableResponseDTO response = equipmentService.getEquipmentAvailability(1L);

        assertEquals(8, response.getAvailableQuantity());
        verify(equipmentRepository).findById(1L);
        verify(equipmentRepository).findActiveAllotments(1L);
    }

    @Test
    void getEquipmentAvailability_whenEquipmentNotFound_throwsResourceNotFound() {
        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> equipmentService.getEquipmentAvailability(1L));

        verify(equipmentRepository).findById(1L);
        verify(equipmentRepository, never()).findActiveAllotments(anyLong());
    }
}
