package com.cognizant.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class EquipmentDTO {

    private Long equipment_id; // Optional for create

    @NotBlank(message = "Equipment name cannot be blank")
    @Size(min = 2, max = 50, message = "Equipment name must be between 2 and 50 characters")
    private String equipment_name;

    @Min(value = 1, message = "Equipment quantity must be greater than 0")
    private int equipment_quantity;

    @NotNull(message = "Game ID cannot be null")
    private Long game_id;

    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    private List<Long> allotmentIds;

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

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }

    public List<Long> getAllotmentIds() {
        return allotmentIds;
    }

    public void setAllotmentIds(List<Long> allotmentIds) {
        this.allotmentIds = allotmentIds;
    }
}