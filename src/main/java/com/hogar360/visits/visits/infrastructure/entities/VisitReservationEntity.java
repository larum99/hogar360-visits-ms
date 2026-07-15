package com.hogar360.visits.visits.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private VisitEntity visit;

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
