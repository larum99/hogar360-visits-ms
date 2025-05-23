package com.hogar360.visits.visits.domain.ports.in;

import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.infrastructure.feigns.dto.HouseResponse;

public interface VisitServicePort {
    void saveVisit(VisitModel visitModel, Long sellerId, String role);
}
