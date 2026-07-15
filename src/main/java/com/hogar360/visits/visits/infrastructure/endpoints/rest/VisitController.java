package com.hogar360.visits.visits.infrastructure.endpoints.rest;

import com.hogar360.visits.commons.configurations.config.ControllerConstants;
import com.hogar360.visits.commons.configurations.config.SwaggerExamples;
import com.hogar360.visits.commons.configurations.config.VisitControllerDocs.*;
import com.hogar360.visits.visits.application.dto.request.ListVisitsRequest;
import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.PagedVisitResponse;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;
import com.hogar360.visits.visits.application.services.VisitService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.hogar360.visits.commons.configurations.config.ControllerConstants.BEARER_PREFIX;

@RestController
@RequestMapping(ControllerConstants.BASE_URL_VISITS)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG_VISITS, description = ControllerConstants.TAG_DESCRIPTION_VISITS)
public class VisitController {

    private final VisitService visitService;

    @CreateVisitDoc
    @PreAuthorize(ControllerConstants.ROLE_SELLER)
    @PostMapping(ControllerConstants.PATH)
    public ResponseEntity<SaveVisitResponse> createVisit(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveVisitRequest saveVisitRequest) {

        String token = authorizationHeader.replace(BEARER_PREFIX, "");
        SaveVisitResponse response = visitService.saveVisit(saveVisitRequest, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ListVisitsDoc
    @GetMapping(ControllerConstants.SEARCH_PATH)
    public ResponseEntity<PagedVisitResponse> listVisits(
            @Parameter(description = SwaggerExamples.START_DATE_TIME_DESCRIPTION, example = SwaggerExamples.START_DATE_TIME_EXAMPLE)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @Parameter(description = SwaggerExamples.END_DATE_TIME_DESCRIPTION, example = SwaggerExamples.END_DATE_TIME_EXAMPLE)
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
            @Parameter(description = SwaggerExamples.CITY_ID_DESCRIPTION, example = SwaggerExamples.CITY_ID_EXAMPLE)
            @RequestParam(required = false) Long cityId,
            @Parameter(description = SwaggerExamples.SECTOR_DESCRIPTION, example = SwaggerExamples.SECTOR_EXAMPLE)
            @RequestParam(required = false) String sector,
            @Parameter(description = SwaggerExamples.PAGE_DESCRIPTION, example = SwaggerExamples.PAGE_EXAMPLE)
            @RequestParam(defaultValue = SwaggerExamples.PAGE_EXAMPLE) int page,
            @Parameter(description = SwaggerExamples.SIZE_DESCRIPTION, example = SwaggerExamples.SIZE_EXAMPLE)
            @RequestParam(defaultValue = SwaggerExamples.SIZE_EXAMPLE) int size,
            @Parameter(description = SwaggerExamples.SORT_BY_DESCRIPTION, example = SwaggerExamples.SORT_BY_VISIT_EXAMPLE)
            @RequestParam(required = false) String sortBy,
            @Parameter(description = SwaggerExamples.SORT_DIRECTION_DESCRIPTION, example = SwaggerExamples.SORT_DIRECTION_EXAMPLE)
            @RequestParam(required = false) String sortDirection
    ) {
        ListVisitsRequest request = new ListVisitsRequest(
                startDateTime, endDateTime, cityId, sector,
                page, size, sortBy, sortDirection
        );

        PagedVisitResponse response = visitService.listVisits(request);
        return ResponseEntity.ok(response);
    }

    @GetAvailableVisitsDoc
    @GetMapping(ControllerConstants.AVAILABLE_PATH)
    public ResponseEntity<List<VisitResponse>> getAvailableVisitsByHouseId(@PathVariable Long houseId) {
        List<VisitResponse> availableVisits = visitService.getAvailableVisitsByHouseId(houseId);

        return ResponseEntity.ok(availableVisits);
    }
}
