package com.hogar360.visits.visits.application.dto.request;

public record SaveVisitReservationRequest(
        Long visitId,
        String buyerEmail
) {
}
