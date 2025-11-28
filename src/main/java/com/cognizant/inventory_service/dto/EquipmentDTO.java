package com.cognizant.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class EquipmentDTO {

    private Long equipmentId; // Optional for create

    @NotBlank(message = "Equipment name cannot be blank")
    @Size(min = 2, max = 50, message = "Equipment name must be between 2 and 50 characters")
    private String equipmentName;

    @Min(value = 1, message = "Equipment quantity must be greater than 0")
    private int equipmentQuantity;

    @NotNull(message = "Game ID cannot be null")
    private Long gameId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<Long> allotmentIds;

    // Getters and Setters
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(int equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public List<Long> getAllotmentIds() {
        return allotmentIds;
    }

    public void setAllotmentIds(List<Long> allotmentIds) {
        this.allotmentIds = allotmentIds;
    }
}