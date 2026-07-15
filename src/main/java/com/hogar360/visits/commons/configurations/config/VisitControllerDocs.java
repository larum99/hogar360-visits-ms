package com.hogar360.visits.commons.configurations.config;

import com.hogar360.visits.visits.application.dto.request.SaveVisitRequest;
import com.hogar360.visits.visits.application.dto.response.PagedVisitResponse;
import com.hogar360.visits.visits.application.dto.response.SaveVisitResponse;
import com.hogar360.visits.visits.application.dto.response.VisitResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

public class VisitControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Crear disponibilidad de visita",
            description = "Permite que un vendedor o agente cree una disponibilidad de visita para un inmueble",
            requestBody = @RequestBody(
                    description = "Datos para crear la disponibilidad de visita",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveVisitRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación de visita",
                                    summary = "Crear disponibilidad",
                                    description = "El vendedor crea una disponibilidad para mostrar la casa",
                                    value = SwaggerExamples.CREATE_VISIT_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Disponibilidad de visita creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveVisitResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Disponibilidad creada",
                                    description = "Respuesta cuando se crea exitosamente una disponibilidad",
                                    value = SwaggerExamples.CREATE_VISIT_RESPONSE
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
    public @interface CreateVisitDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Listar visitas",
            description = "Permite listar visitas filtradas por fecha, ubicación y paginadas",
            parameters = {
                    @Parameter(
                            name = "startDateTime",
                            description = SwaggerExamples.START_DATE_TIME_DESCRIPTION,
                            example = SwaggerExamples.START_DATE_TIME_EXAMPLE
                    ),
                    @Parameter(
                            name = "endDateTime",
                            description = SwaggerExamples.END_DATE_TIME_DESCRIPTION,
                            example = SwaggerExamples.END_DATE_TIME_EXAMPLE
                    ),
                    @Parameter(
                            name = "cityId",
                            description = SwaggerExamples.CITY_ID_DESCRIPTION,
                            example = SwaggerExamples.CITY_ID_EXAMPLE
                    ),
                    @Parameter(
                            name = "sector",
                            description = SwaggerExamples.SECTOR_DESCRIPTION,
                            example = SwaggerExamples.SECTOR_EXAMPLE
                    ),
                    @Parameter(
                            name = "page",
                            description = SwaggerExamples.PAGE_DESCRIPTION,
                            example = SwaggerExamples.PAGE_EXAMPLE
                    ),
                    @Parameter(
                            name = "size",
                            description = SwaggerExamples.SIZE_DESCRIPTION,
                            example = SwaggerExamples.SIZE_EXAMPLE
                    ),
                    @Parameter(
                            name = "sortBy",
                            description = SwaggerExamples.SORT_BY_DESCRIPTION,
                            example = SwaggerExamples.SORT_BY_VISIT_EXAMPLE
                    ),
                    @Parameter(
                            name = "sortDirection",
                            description = SwaggerExamples.SORT_DIRECTION_DESCRIPTION,
                            example = SwaggerExamples.SORT_DIRECTION_EXAMPLE
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de visitas paginado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedVisitResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Visitas encontradas",
                                    description = "Respuesta con las visitas que cumplen con los filtros",
                                    value = SwaggerExamples.PAGED_VISITS_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida en parámetros",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error de validación",
                                    summary = "Parámetros inválidos",
                                    description = "Los parámetros de búsqueda no son válidos",
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
    public @interface ListVisitsDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Listar visitas disponibles por inmueble",
            description = "Obtiene las visitas disponibles para un inmueble específico, considerando que no tengan 2 o más reservas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de visitas disponibles",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VisitResponse.class)),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Visitas disponibles",
                                    description = "Lista de visitas disponibles para el inmueble",
                                    value = SwaggerExamples.VISITS_AVAILABLE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inmueble no encontrado o sin visitas disponibles",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "No encontrado",
                                    summary = "Sin resultados",
                                    description = "No se encontraron visitas disponibles para el inmueble",
                                    value = SwaggerExamples.NOT_FOUND_RESPONSE
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
    public @interface GetAvailableVisitsDoc {}
}
