package com.cognizant.inventory_service.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class EquipmentAvailableResponseDTO {
    private int availableQuantity;
}
