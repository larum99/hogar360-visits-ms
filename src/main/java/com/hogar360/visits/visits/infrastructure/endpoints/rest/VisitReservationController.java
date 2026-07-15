package com.hogar360.visits.visits.infrastructure.endpoints.rest;

import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitReservationResponse;
import com.hogar360.visits.visits.application.services.VisitReservationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visits/reservations")
@RequiredArgsConstructor
public class VisitReservationController {

    private final VisitReservationService visitReservationService;

    @PostMapping("/")
    public ResponseEntity<SaveVisitReservationResponse> reserveVisit(
            @RequestBody SaveVisitReservationRequest request) {

        SaveVisitReservationResponse response = visitReservationService.reserveVisit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
