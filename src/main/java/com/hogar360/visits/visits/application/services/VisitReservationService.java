package com.hogar360.visits.visits.application.services;

import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitReservationResponse;

public interface VisitReservationService {
    SaveVisitReservationResponse reserveVisit(SaveVisitReservationRequest request);
}
