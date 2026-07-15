package com.hogar360.visits.visits.application.mappers;

import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;
import com.hogar360.visits.visits.domain.model.VisitModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitDtoMapper {
    VisitModel requestToModel(SaveVisitRequest saveVisitRequest);

    VisitResponse modelToResponse(VisitModel model);

    List<VisitResponse> modelToResponseList(List<VisitModel> models);
}
