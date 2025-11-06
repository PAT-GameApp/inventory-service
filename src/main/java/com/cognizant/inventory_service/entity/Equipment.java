package com.cognizant.inventory_service.entity;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
 
@Entity
@Table(name = "equipment")
public class Equipment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long equipment_id;
 
    @Column(name = "equipment_name", nullable = false)
    private String equipment_name;
 
    @Column(name = "equipment_quantity", nullable = false)
    private int equipment_quantity;
 
    @Column(name = "game_id", nullable = false)
    private Long game_id; // References game table (from Game Catalog Service)
 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;
 
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modified_at;
 
    // Relationship with Allotments table
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Allotment> allotments;
 
    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.modified_at = LocalDateTime.now();
    }
 
    @PreUpdate
    protected void onUpdate() {
        this.modified_at = LocalDateTime.now();
    }
 
    // Getters and Setters
    public Long getEquipment_id() {
        return equipment_id;
    }
 
    public void setEquipment_id(Long equipment_id) {
        this.equipment_id = equipment_id;
    }
 
    public String getEquipment_name() {
        return equipment_name;
    }
 
    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }
 
    public int getEquipment_quantity() {
        return equipment_quantity;
    }
 
    public void setEquipment_quantity(int equipment_quantity) {
        this.equipment_quantity = equipment_quantity;
    }
 
    public Long getGame_id() {
        return game_id;
    }
 
    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }
 
    public LocalDateTime getCreated_at() {
        return created_at;
    }
 
    public LocalDateTime getModified_at() {
        return modified_at;
    }
 
    public List<Allotment> getAllotments() {
        return allotments;
    }
 
    public void setAllotments(List<Allotment> allotments) {
        this.allotments = allotments;
    }
}
 