package com.hogar360.visits.visits.application.mappers;

import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.VisitReservationResponse;
import com.hogar360.visits.visits.domain.model.VisitReservationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitReservationDtoMapper {

    @Mapping(source = "visitId", target = "visit.id")
    VisitReservationModel requestToModel(SaveVisitReservationRequest dto);

    @Mapping(source = "visit.id", target = "visitId")
    VisitReservationResponse modelToResponse(VisitReservationModel model);
}

