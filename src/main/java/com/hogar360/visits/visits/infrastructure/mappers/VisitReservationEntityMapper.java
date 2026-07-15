package com.hogar360.visits.visits.infrastructure.mappers;

import com.hogar360.visits.visits.domain.model.VisitReservationModel;
import com.hogar360.visits.visits.infrastructure.entities.VisitReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitReservationEntityMapper {
    VisitReservationModel entityToModel(VisitReservationEntity entity);
    VisitReservationEntity modelToEntity(VisitReservationModel model);
}
