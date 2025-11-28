package com.cognizant.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AllotmentDTO {

    private Long allotmentId; // Optional for create

    @NotNull(message = "Equipment ID cannot be null")
    private Long equipmentId;

    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Getters and Setters
    public Long getAllotmentId() {
        return allotmentId;
    }

    public void setAllotmentId(Long allotmentId) {
        this.allotmentId = allotmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}