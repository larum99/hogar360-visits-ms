package com.hogar360.visits.visits.domain.ports.in;

import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.utils.PageResult;

import java.util.List;

public interface VisitServicePort {
    void saveVisit(VisitModel visitModel, Long sellerId, String role);
    PageResult<VisitModel> listVisits(VisitCriteria criteria);
    List<VisitModel> getVisitsByLocation(Long cityId, String sector);
    List<VisitModel> getAvailableVisitsByHouseId(Long houseId);
}
