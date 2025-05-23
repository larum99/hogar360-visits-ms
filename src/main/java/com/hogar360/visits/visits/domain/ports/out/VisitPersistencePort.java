package com.hogar360.visits.visits.domain.ports.out;

import com.hogar360.visits.visits.domain.model.VisitModel;

import java.time.LocalDateTime;

public interface VisitPersistencePort {
    void save(VisitModel visitModel);
    boolean hasOverlappingVisits(Long sellerId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
