package com.hogar360.visits.visits.infrastructure.mappers;

import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.infrastructure.entities.VisitEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitEntityMapper {
    VisitModel entityToModel(VisitEntity visitEntity);
    VisitEntity modelToEntity(VisitModel visitModel);
}
