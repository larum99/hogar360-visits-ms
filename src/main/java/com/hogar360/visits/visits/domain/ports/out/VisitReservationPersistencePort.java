package com.hogar360.visits.visits.domain.ports.out;

import com.hogar360.visits.visits.domain.model.VisitReservationModel;

import java.util.Optional;

public interface VisitReservationPersistencePort {
    void save(VisitReservationModel reservationModel);

    int countByVisitId(Long visitId);

    boolean existsByVisitIdAndBuyerEmail(Long visitId, String buyerEmail);

    Optional<VisitReservationModel> findById(Long id);
}
