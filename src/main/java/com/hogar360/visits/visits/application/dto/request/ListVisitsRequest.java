package com.hogar360.visits.visits.application.dto.request;

import java.time.LocalDateTime;

public record ListVisitsRequest(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Long cityId,
        String sector,
        int page,
        int size,
        String sortBy,
        String sortDirection
) {}
