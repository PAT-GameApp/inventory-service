package com.cognizant.inventory_service.service;

import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import com.cognizant.inventory_service.repository.AllotmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllotmentServiceTest {

    @Mock
    private AllotmentRepository allotmentRepository;

    @InjectMocks
    private AllotmentService allotmentService;

    private Allotment allotment;

    @BeforeEach
    void setUp() {
        allotment = new Allotment();
        allotment.setAllotmentId(1L);
        allotment.setUserId(10L);
        allotment.setReturned(false);
    }

    @Test
    void getAllAllotments_withoutSort_returnsAllFromRepository() {
        when(allotmentRepository.findAll()).thenReturn(List.of(allotment));

        List<Allotment> result = allotmentService.getAllAllotments();

        assertThat(result).containsExactly(allotment);
        verify(allotmentRepository).findAll();
    }

    @Test
    void getAllAllotments_withPageable_delegatesToRepository() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Allotment> page = new PageImpl<>(List.of(allotment), pageable, 1);
        when(allotmentRepository.findAll(pageable)).thenReturn(page);

        Page<Allotment> result = allotmentService.getAllAllotments(pageable);

        assertThat(result.getContent()).containsExactly(allotment);
        verify(allotmentRepository).findAll(pageable);
    }

    @Test
    void getAllAllotments_withSorted_returnsSortedFromRepository() {
        Sort sort = Sort.by("userId");
        when(allotmentRepository.findAll(sort)).thenReturn(List.of(allotment));

        List<Allotment> result = allotmentService.getAllAllotments(sort);

        assertThat(result).containsExactly(allotment);
        verify(allotmentRepository).findAll(sort);
    }

    @Test
    void getAllAllotments_withUnsortedSort_fallsBackToFindAll() {
        Sort sort = Sort.unsorted();
        when(allotmentRepository.findAll()).thenReturn(List.of(allotment));

        List<Allotment> result = allotmentService.getAllAllotments(sort);

        assertThat(result).containsExactly(allotment);
        verify(allotmentRepository).findAll();
        verify(allotmentRepository, never()).findAll(sort);
    }

    @Test
    void getAllotmentById_returnsOptionalFromRepository() {
        when(allotmentRepository.findById(1L)).thenReturn(Optional.of(allotment));

        Optional<Allotment> result = allotmentService.getAllotmentById(1L);

        assertThat(result).contains(allotment);
        verify(allotmentRepository).findById(1L);
    }

    @Test
    void addAllotment_savesAndReturnsEntity() {
        when(allotmentRepository.save(allotment)).thenReturn(allotment);

        Allotment result = allotmentService.addAllotment(allotment);

        assertThat(result).isEqualTo(allotment);
        verify(allotmentRepository).save(allotment);
    }

    @Test
    void deleteAllotment_whenIdExists_deletesAndReturnsMessage() {
        when(allotmentRepository.existsById(1L)).thenReturn(true);

        String message = allotmentService.deleteAllotment(1L);

        assertEquals("The id 1 deleted successfully", message);
        verify(allotmentRepository).existsById(1L);
        verify(allotmentRepository).deleteById(1L);
    }

    @Test
    void deleteAllotment_whenIdDoesNotExist_throwsResourceNotFound() {
        when(allotmentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> allotmentService.deleteAllotment(1L));

        verify(allotmentRepository).existsById(1L);
        verify(allotmentRepository, never()).deleteById(anyLong());
    }

    @Test
    void returnAllotment_whenFound_marksReturnedTrueAndSaves() {
        when(allotmentRepository.findById(1L)).thenReturn(Optional.of(allotment));
        when(allotmentRepository.save(any(Allotment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Allotment result = allotmentService.returnAllotment(1L);

        assertThat(result.isReturned()).isTrue();
        verify(allotmentRepository).findById(1L);
        verify(allotmentRepository).save(any(Allotment.class));
    }

    @Test
    void returnAllotment_whenNotFound_throwsResourceNotFound() {
        when(allotmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> allotmentService.returnAllotment(1L));

        verify(allotmentRepository).findById(1L);
        verify(allotmentRepository, never()).save(any(Allotment.class));
    }
}
