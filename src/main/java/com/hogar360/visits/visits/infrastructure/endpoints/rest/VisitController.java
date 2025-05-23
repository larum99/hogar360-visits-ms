package com.hogar360.visits.visits.infrastructure.endpoints.rest;

import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.services.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
@Tag(name = "Visitas", description = "Operaciones relacionadas con la disponibilidad de visitas")
public class VisitController {

    private final VisitService visitService;

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
                                          "houseId": "123e4567-e89b-12d3-a456-426614174000",
                                          "date": "2025-05-01",
                                          "startTime": "10:00",
                                          "endTime": "11:00"
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
                                                  "message": "Disponibilidad de visita creada correctamente."
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida (por ejemplo, datos nulos o solapamiento de horarios)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            summary = "Datos inválidos",
                                            description = "Se envió una solicitud inválida para crear disponibilidad",
                                            value = """
                                                {
                                                  "message": "Datos inválidos para crear disponibilidad de visita.",
                                                  "time": "2025-04-22T00:00:00.000Z"
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
                                                  "time": "2025-04-22T00:00:00.000Z"
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
}
