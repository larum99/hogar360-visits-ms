package com.hogar360.visits.visits.application.dto.response;

import java.util.List;

public record PagedVisitResponse(
        List<VisitResponse> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean isFirst,
        boolean isLast
) {
}
