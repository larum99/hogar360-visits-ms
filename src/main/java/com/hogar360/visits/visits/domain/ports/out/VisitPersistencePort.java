package com.hogar360.visits.visits.domain.ports.out;

import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.utils.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitPersistencePort {
    void save(VisitModel visitModel);
    boolean hasOverlappingVisits(Long sellerId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    Optional<VisitModel> findById(Long id);
    PageResult<VisitModel> list(VisitCriteria criteria);
    List<VisitModel> findByHouseIds(List<Long> houseIds);
    List<VisitModel> findAvailableVisitsByHouseId(Long houseId);
}
