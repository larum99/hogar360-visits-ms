package com.hogar360.visits.visits.infrastructure.feigns.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HouseResponse(
        Long id,
        String name,
        String description,
        CategoryResponse category,
        int bedrooms,
        int bathrooms,
        BigDecimal price,
        LocationResponse location,
        LocalDate publicationDate,
        LocalDate activePublicationDate,
        String status,
        Long publisherId
) {}
