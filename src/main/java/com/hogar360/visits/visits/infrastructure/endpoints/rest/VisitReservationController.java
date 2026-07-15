package com.hogar360.visits.visits.infrastructure.endpoints.rest;

import com.hogar360.visits.commons.configurations.config.ControllerConstants;
import com.hogar360.visits.commons.configurations.config.VisitReservationControllerDocs.*;
import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitReservationResponse;
import com.hogar360.visits.visits.application.services.VisitReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL_RESERVATIONS)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG_RESERVATIONS, description = ControllerConstants.TAG_DESCRIPTION_RESERVATIONS)
public class VisitReservationController {

    private final VisitReservationService visitReservationService;

    @ReserveVisitDoc
    @PostMapping(ControllerConstants.PATH)
    public ResponseEntity<SaveVisitReservationResponse> reserveVisit(
            @RequestBody SaveVisitReservationRequest request) {

        SaveVisitReservationResponse response = visitReservationService.reserveVisit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
