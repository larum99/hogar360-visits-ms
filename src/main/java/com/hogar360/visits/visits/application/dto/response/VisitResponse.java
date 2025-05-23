package com.hogar360.visits.visits.application.dto.response;

import java.time.LocalDateTime;

public record VisitResponse(
        Long id,
        Long userId,
        Long houseId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
