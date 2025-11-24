package com.cognizant.inventory_service.service;

import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.repository.AllotmentRepository;
import com.cognizant.inventory_service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Allotment> getAllotmentById(Integer id) {
        return allotmentRepository.findById(id);
    }

    public Allotment addAllotment(Allotment allotment) {
        return allotmentRepository.save(allotment);
    }

    // ✅ Updated delete method
    public String deleteAllotment(Integer id) {
        if (!allotmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("The id " + id + " does not exist");
        }
        allotmentRepository.deleteById(id);
        return "The id " + id + " deleted successfully";
    }
}