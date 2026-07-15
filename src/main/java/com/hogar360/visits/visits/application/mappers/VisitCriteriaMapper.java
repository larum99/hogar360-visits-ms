package com.hogar360.visits.visits.application.mappers;

import com.hogar360.visits.visits.application.dto.request.ListVisitsRequest;
import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitCriteriaMapper {

    VisitCriteria requestToCriteria(ListVisitsRequest listVisitsRequest);

}
