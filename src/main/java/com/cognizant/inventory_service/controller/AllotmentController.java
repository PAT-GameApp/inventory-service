package com.cognizant.inventory_service.controller;
 
import com.cognizant.inventory_service.entity.Allotment;
import com.cognizant.inventory_service.service.AllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/allotments")
public class AllotmentController {
 
    @Autowired
    private AllotmentService allotmentService;
 
    @GetMapping
    public List<Allotment> getAllAllotments() {
        return allotmentService.getAllAllotments();
    }
 
    @GetMapping("/{id}")
    public Optional<Allotment> getAllotmentById(@PathVariable Long id) {
        return allotmentService.getAllotmentById(id);
    }
 
    @PostMapping
    public Allotment addAllotment(@RequestBody Allotment allotment) {
        return allotmentService.addAllotment(allotment);
    }
 
    @DeleteMapping("/{id}")
    public void deleteAllotment(@PathVariable Long id) {
        allotmentService.deleteAllotment(id);
    }
}