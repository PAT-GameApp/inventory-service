package com.cognizant.inventory_service.entity;
 
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "allotments")
public class Allotment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allotment_id;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private int user_id;
 
    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at = LocalDateTime.now();
 
    // Getters and Setters
    public int getAllotment_id() {
        return allotment_id;
    }
 
    public void setAllotment_id(int allotment_id) {
        this.allotment_id = allotment_id;
    }
 

    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }

 
    public int getUser_id() {
        return user_id;
    }
 
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 
    public LocalDateTime getCreated_at() {
        return created_at;
    }
 
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
 
    public LocalDateTime getModified_at() {
        return modified_at;
    }
 
    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }
}
 