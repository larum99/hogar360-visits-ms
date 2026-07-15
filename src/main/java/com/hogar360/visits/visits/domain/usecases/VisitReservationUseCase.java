package com.hogar360.visits.visits.domain.usecases;

import com.hogar360.visits.visits.domain.utils.constants.DomainConstants;
import com.hogar360.visits.visits.domain.exceptions.*;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.model.VisitReservationModel;
import com.hogar360.visits.visits.domain.ports.in.VisitReservationServicePort;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.ports.out.VisitReservationPersistencePort;

import java.time.LocalDateTime;
import java.util.Objects;

public class VisitReservationUseCase implements VisitReservationServicePort {

    private final VisitPersistencePort visitPersistencePort;
    private final VisitReservationPersistencePort reservationPersistencePort;

    public VisitReservationUseCase(VisitPersistencePort visitPersistencePort,
                                   VisitReservationPersistencePort reservationPersistencePort) {
        this.visitPersistencePort = visitPersistencePort;
        this.reservationPersistencePort = reservationPersistencePort;
    }

    @Override
    public void reserveVisit(VisitReservationModel reservationModel) {

        validateMandatoryFields(reservationModel);

        VisitModel visit = visitPersistencePort.findById(reservationModel.getVisit().getId())
                .orElseThrow(VisitNotFoundException::new);

        if (visit.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new VisitAlreadyStartedException();
        }

        int count = reservationPersistencePort.countByVisitId(visit.getId());
        if (count >= DomainConstants.MAX_RESERVATIONS_PER_VISIT) {
            throw new VisitFullyBookedException();
        }

        boolean alreadyReserved = reservationPersistencePort.existsByVisitIdAndBuyerEmail(
                visit.getId(), reservationModel.getBuyerEmail());
        if (alreadyReserved) {
            throw new BuyerAlreadyBookedVisitException();
        }

        reservationModel.setVisit(visit);
        reservationModel.setCreatedAt(LocalDateTime.now());

        reservationPersistencePort.save(reservationModel);
    }

    private void validateMandatoryFields(VisitReservationModel reservationModel) {
        Objects.requireNonNull(reservationModel.getVisit(), DomainConstants.FIELD_VISIT_NULL_MESSAGE);
        Objects.requireNonNull(reservationModel.getVisit().getId(), DomainConstants.FIELD_VISIT_ID_NULL_MESSAGE);
        Objects.requireNonNull(reservationModel.getBuyerEmail(), DomainConstants.FIELD_BUYER_EMAIL_NULL_MESSAGE);
    }
}