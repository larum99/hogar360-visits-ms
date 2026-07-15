package com.hogar360.visits.visits.infrastructure.specifications;

import com.hogar360.visits.visits.infrastructure.entities.VisitEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class VisitSpecification {

    public static Specification<VisitEntity> hasStartDateTimeAfter(LocalDateTime startDateTime) {
        return (root, query, builder) -> {
            if (startDateTime == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("startDateTime"), startDateTime);
        };
    }

    public static Specification<VisitEntity> hasEndDateTimeBefore(LocalDateTime endDateTime) {
        return (root, query, builder) -> {
            if (endDateTime == null) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get("endDateTime"), endDateTime);
        };
    }

    public static Specification<VisitEntity> hasHouseIdIn(List<Long> houseIds) {
        return (root, query, builder) -> {
            if (houseIds == null || houseIds.isEmpty()) {
                return null;
            }
            return root.get("houseId").in(houseIds);
        };
    }
}
