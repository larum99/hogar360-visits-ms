package com.hogar360.visits.visits.infrastructure.endpoints.rest;

import com.hogar360.visits.visits.application.dto.request.ListVisitsRequest;
import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.PagedVisitResponse;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;
import com.hogar360.visits.visits.application.services.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
@Tag(name = "Visitas", description = "Operaciones relacionadas con la disponibilidad de visitas")
public class VisitController {

    private final VisitService visitService;

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/")
    @Operation(
            summary = "Crear disponibilidad de visita",
            description = "Permite que un vendedor o agente cree una disponibilidad de visita para un inmueble",
            requestBody = @RequestBody(
                    description = "Datos para crear la disponibilidad de visita",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación de visita",
                                    summary = "Crear disponibilidad",
                                    description = "El usuario crea una disponibilidad para mostrar la casa",
                                    value = """
                                        {
                                          "houseId": 123,
                                          "startDateTime": "2025-05-01T10:00:00",
                                          "endDateTime": "2025-05-01T11:00:00"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Disponibilidad de visita creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Respuesta exitosa",
                                            summary = "Disponibilidad creada",
                                            description = "Respuesta cuando se crea exitosamente una disponibilidad",
                                            value = """
                                                {
                                                  "message": "Disponibilidad de visita creada correctamente.",
                                                  "time": "2025-04-22T00:00:00"
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida (datos nulos o solapamiento de horarios)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            summary = "Datos inválidos",
                                            description = "Solicitud inválida para crear disponibilidad",
                                            value = """
                                                {
                                                  "message": "Datos inválidos para crear disponibilidad de visita.",
                                                  "time": "2025-04-22T00:00:00"
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            summary = "Fallo inesperado",
                                            description = "Algo salió mal en el servidor",
                                            value = """
                                                {
                                                  "message": "Error inesperado. Intente más tarde.",
                                                  "time": "2025-04-22T00:00:00"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<SaveVisitResponse> createVisit(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @org.springframework.web.bind.annotation.RequestBody SaveVisitRequest saveVisitRequest) {

        String token = authorizationHeader.replace("Bearer ", "");
        SaveVisitResponse response = visitService.saveVisit(saveVisitRequest, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Listar visitas",
            description = "Permite listar visitas filtradas por fecha, ubicación y paginadas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de visitas paginado",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida en parámetros",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<PagedVisitResponse> listVisits(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) String sector,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {
        ListVisitsRequest request = new ListVisitsRequest(
                startDateTime,
                endDateTime,
                cityId,
                sector,
                page,
                size,
                sortBy,
                sortDirection
        );

        PagedVisitResponse response = visitService.listVisits(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available/{houseId}")
    @Operation(
            summary = "Listar visitas disponibles por inmueble",
            description = "Obtiene las visitas disponibles para un inmueble específico, considerando que no tengan 2 o más reservas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de visitas disponibles",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Inmueble no encontrado o sin visitas disponibles",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<VisitResponse>> getAvailableVisitsByHouseId(@PathVariable Long houseId) {
        List<VisitResponse> availableVisits = visitService.getAvailableVisitsByHouseId(houseId);

        return ResponseEntity.ok(availableVisits);
    }
}
