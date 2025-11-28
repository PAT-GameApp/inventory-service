package com.cognizant.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "allotments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "equipment") // Prevent circular reference
public class Allotment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allotmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipmentId", nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}