package com.hogar360.visits.visits.application.dto.response;

import java.time.LocalDateTime;

public record VisitReservationResponse(
        Long id,
        Long visitId,
        String buyerEmail,
        LocalDateTime createdAt
) {
}
