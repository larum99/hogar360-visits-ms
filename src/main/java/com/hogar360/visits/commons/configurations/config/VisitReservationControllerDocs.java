package com.hogar360.visits.commons.configurations.config;

import com.hogar360.visits.visits.application.dto.request.SaveVisitReservationRequest;
import com.hogar360.visits.visits.application.dto.response.SaveVisitReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class VisitReservationControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Reservar una visita",
            description = "Permite a un comprador reservar una visita disponible (máximo 2 reservas por visita)",
            requestBody = @RequestBody(
                    description = "Datos para reservar la visita",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveVisitReservationRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de reserva de visita",
                                    summary = "Reservar visita",
                                    description = "El comprador reserva una visita disponible",
                                    value = SwaggerExamples.RESERVE_VISIT_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Visita reservada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveVisitReservationResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Reserva creada",
                                    description = "Respuesta cuando se crea exitosamente una reserva",
                                    value = SwaggerExamples.RESERVE_VISIT_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida (visita llena o datos incorrectos)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error de validación",
                                    summary = "Datos inválidos",
                                    description = "Solicitud inválida para reservar visita",
                                    value = SwaggerExamples.INVALID_DATA_RESPONSE
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
                                    value = SwaggerExamples.ERROR_RESPONSE
                            )
                    )
            )
    })
    public @interface ReserveVisitDoc {}
}
