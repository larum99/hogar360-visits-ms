package com.hogar360.visits.visits.application.dto.request;

import java.time.LocalDateTime;

public record SaveVisitRequest(
        Long houseId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
