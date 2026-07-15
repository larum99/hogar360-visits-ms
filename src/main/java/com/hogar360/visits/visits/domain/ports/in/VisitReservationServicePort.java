package com.hogar360.visits.visits.domain.ports.in;

import com.hogar360.visits.visits.domain.model.VisitReservationModel;

public interface VisitReservationServicePort {
    void reserveVisit(VisitReservationModel reservationModel);
}
