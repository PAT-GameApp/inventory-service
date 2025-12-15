package com.cognizant.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllotmentDTO {

    private Long allotmentId;

    private Long equipmentId;

    private boolean returned;

    private Long bookingId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
