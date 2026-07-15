package com.hogar360.visits.visits.application.services.impl;

import com.hogar360.visits.commons.configurations.utils.Constants;
import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitReservationResponse;
import com.hogar360.visits.visits.application.mappers.VisitReservationDtoMapper;
import com.hogar360.visits.visits.application.services.VisitReservationService;
import com.hogar360.visits.visits.domain.ports.in.VisitReservationServicePort;
import com.hogar360.visits.visits.domain.model.VisitReservationModel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitReservationServiceImpl implements VisitReservationService {

    private final VisitReservationServicePort reservationServicePort;
    private final VisitReservationDtoMapper reservationDtoMapper;

    @Override
    public SaveVisitReservationResponse reserveVisit(SaveVisitReservationRequest request) {
        VisitReservationModel reservationModel = reservationDtoMapper.requestToModel(request);
        reservationServicePort.reserveVisit(reservationModel);
        return new SaveVisitReservationResponse(Constants.SAVE_VISIT_RESERVATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
