package com.cognizant.inventory_service.service;
 
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.repository.AllotmentRepository;
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
 
    public Optional<Allotment> getAllotmentById(Long id) {
        return allotmentRepository.findById(id);
    }
 
    public Allotment addAllotment(Allotment allotment) {
        return allotmentRepository.save(allotment);
    }
 
    public void deleteAllotment(Long id) {
        allotmentRepository.deleteById(id);
    }
}