package com.hogar360.visits.visits.application.dto.response;

import java.time.LocalDateTime;

public record SaveVisitReservationResponse(
        String message,
        LocalDateTime time
) {
}
