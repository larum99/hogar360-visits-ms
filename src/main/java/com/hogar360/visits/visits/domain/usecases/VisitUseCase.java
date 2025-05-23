package com.hogar360.visits.visits.domain.usecases;

import com.hogar360.visits.visits.domain.exceptions.DateOutOfRangeException;
import com.hogar360.visits.visits.domain.exceptions.EndDateBeforeStartDateException;
import com.hogar360.visits.visits.domain.exceptions.ForbiddenException;
import com.hogar360.visits.visits.domain.exceptions.HouseNotFoundException;
import com.hogar360.visits.visits.domain.exceptions.InvalidVisitDurationException;
import com.hogar360.visits.visits.domain.exceptions.StartDateBeforeNowException;
import com.hogar360.visits.visits.domain.exceptions.UserIsNotHouseOwnerException;
import com.hogar360.visits.visits.domain.exceptions.VisitOverlapException;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.ports.in.VisitServicePort;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.domain.utils.constants.DomainConstants;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional; // Importar Optional

@RequiredArgsConstructor
public class VisitUseCase implements VisitServicePort {

    private final VisitPersistencePort visitPersistencePort;
    private final HouseServicePort houseServicePort; // Puerto que retorna Optional

    @Override
    public void saveVisit(VisitModel visitModel, Long sellerId, String role) {

        // Validaciones de Negocio
        validateRole(role); // Verifica si el usuario es vendedor
        validateMandatoryFields(visitModel); // Verifica campos requeridos en el modelo (HouseId, Fechas)
        validateDates(visitModel.getStartDateTime(), visitModel.getEndDateTime()); // Verifica rango y orden de fechas

        // Validar Solapamiento usando Driven Port de Persistencia
        boolean hasOverlap = visitPersistencePort.hasOverlappingVisits(sellerId, visitModel.getStartDateTime(), visitModel.getEndDateTime());
        if (hasOverlap) {
            throw new VisitOverlapException(); // Excepción sin mensaje, manejada en infra
        }

        // Validar Propiedad usando Driven Port del Servicio de Casas
        Optional<Long> ownerIdOptional = houseServicePort.getOwnerId(visitModel.getHouseId()); // Llama al puerto que retorna Optional

        if (ownerIdOptional.isEmpty()) { // Validar si la casa fue encontrada
            throw new HouseNotFoundException(); // Excepción sin mensaje
        }

        Long ownerId = ownerIdOptional.get(); // Obtiene el ownerId si está presente en el Optional

        if (!ownerId.equals(sellerId)) { // Validar si el vendedor es el propietario
            throw new UserIsNotHouseOwnerException(); // Excepción sin mensaje
        }

        // Si todas las validaciones pasan, finalizar el modelo y guardar
        visitModel.setUserId(sellerId); // Asigna el sellerId al modelo de visita

        visitPersistencePort.save(visitModel); // Guarda la visita usando el Driven Port de Persistencia
    }

    // Métodos de validación privados (sin mensajes en excepciones donde aplica)
    private void validateMandatoryFields(VisitModel visitModel) {
        Objects.requireNonNull(visitModel.getHouseId(), DomainConstants.FIELD_HOUSE_ID_NULL_MESSAGE);
        Objects.requireNonNull(visitModel.getStartDateTime(), DomainConstants.FIELD_START_DATE_NULL_MESSAGE);
        Objects.requireNonNull(visitModel.getEndDateTime(), DomainConstants.FIELD_END_DATE_NULL_MESSAGE);
    }

    private void validateDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeWeeksLater = now.plusWeeks(3);

        if (startDateTime.isBefore(now)) {
            throw new StartDateBeforeNowException();
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new EndDateBeforeStartDateException();
        }
        if (startDateTime.isAfter(threeWeeksLater) || endDateTime.isAfter(threeWeeksLater)) {
            throw new DateOutOfRangeException();
        }
        if (startDateTime.isEqual(endDateTime) || endDateTime.isBefore(startDateTime)) {
            throw new InvalidVisitDurationException();
        }
    }

    private void validateRole(String role) {
        if (!DomainConstants.ROLE_SELLER.equals(role)) {
            throw new ForbiddenException();
        }
    }
}