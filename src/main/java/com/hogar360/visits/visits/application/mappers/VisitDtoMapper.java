package com.hogar360.visits.visits.application.mappers;

import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.domain.model.VisitModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitDtoMapper {
    VisitModel requestToModel(SaveVisitRequest saveVisitRequest);
}
