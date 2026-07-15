package com.hogar360.visits.visits.domain.model;

import java.time.LocalDateTime;

public class VisitReservationModel {
    private Long id;
    private VisitModel visit;
    private String buyerEmail;
    private LocalDateTime createdAt;

    public VisitReservationModel(Long id, VisitModel visit, String buyerEmail, LocalDateTime createdAt) {
        this.id = id;
        this.visit = visit;
        this.buyerEmail = buyerEmail;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitModel getVisit() {
        return visit;
    }

    public void setVisit(VisitModel visit) {
        this.visit = visit;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
