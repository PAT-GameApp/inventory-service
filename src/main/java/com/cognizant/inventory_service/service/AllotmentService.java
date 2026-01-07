package com.cognizant.inventory_service.service;

import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.repository.AllotmentRepository;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllotmentService {

    @Autowired
    private AllotmentRepository allotmentRepository;

    public List<Allotment> getAllAllotments() {
        return allotmentRepository.findAll();
    }

    public Page<Allotment> getAllAllotments(Pageable pageable) {
        return allotmentRepository.findAll(pageable);
    }

    public List<Allotment> getAllAllotments(Sort sort) {
        return sort == null || sort.isUnsorted() ? allotmentRepository.findAll() : allotmentRepository.findAll(sort);
    }

    public Optional<Allotment> getAllotmentById(Long id) {
        return allotmentRepository.findById(id);
    }

    public Allotment addAllotment(Allotment allotment) {
        return allotmentRepository.save(allotment);
    }

    // Updated delete method
    public String deleteAllotment(Long id) {
        if (!allotmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("The id " + id + " does not exist");
        }
        allotmentRepository.deleteById(id);
        return "The id " + id + " deleted successfully";
    }

    public Allotment returnAllotment(Long id) {
        Allotment allotment = allotmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allotment not found with id " + id));
        allotment.setReturned(true);
        return allotmentRepository.save(allotment);
    }
}
