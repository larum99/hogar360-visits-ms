package com.hogar360.visits.visits.domain.model;

import java.time.LocalDateTime;

public class VisitModel {
    private Long id;
    private Long userId;
    private Long houseId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public VisitModel(Long id, Long userId, Long houseId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.userId = userId;
        this.houseId = houseId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
