package com.hogar360.visits.commons.configurations.config;

public class SwaggerExamples {

    private SwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_VISIT_REQUEST = """
        {
          "houseId": 123,
          "startDateTime": "2025-05-01T10:00:00",
          "endDateTime": "2025-05-01T11:00:00"
        }
    """;

    public static final String CREATE_VISIT_RESPONSE = """
        {
          "message": "Disponibilidad de visita creada correctamente.",
          "time": "2025-04-22T00:00:00"
        }
    """;

    public static final String PAGED_VISITS_RESPONSE = """
        {
          "content": [
            {
              "id": 1,
              "userId": 10,
              "houseId": 123,
              "startDateTime": "2025-05-01T10:00:00",
              "endDateTime": "2025-05-01T11:00:00"
            },
            {
              "id": 2,
              "userId": 11,
              "houseId": 456,
              "startDateTime": "2025-05-02T14:00:00",
              "endDateTime": "2025-05-02T15:00:00"
            }
          ],
          "totalElements": 2,
          "totalPages": 1,
          "currentPage": 0,
          "pageSize": 10,
          "isFirst": true,
          "isLast": true
        }
    """;

    public static final String VISITS_AVAILABLE_RESPONSE = """
        [
          {
            "id": 1,
            "userId": 10,
            "houseId": 123,
            "startDateTime": "2025-05-01T10:00:00",
            "endDateTime": "2025-05-01T11:00:00"
          }
        ]
    """;

    public static final String RESERVE_VISIT_REQUEST = """
        {
          "visitId": 1,
          "buyerEmail": "comprador@ejemplo.com"
        }
    """;

    public static final String RESERVE_VISIT_RESPONSE = """
        {
          "message": "Visita reservada exitosamente.",
          "time": "2025-04-22T00:00:00"
        }
    """;

    public static final String INVALID_DATA_RESPONSE = """
        {
          "message": "Datos inválidos para la solicitud.",
          "time": "2025-04-22T00:00:00"
        }
    """;

    public static final String NOT_FOUND_RESPONSE = """
        {
          "message": "Inmueble no encontrado o sin visitas disponibles.",
          "time": "2025-04-22T00:00:00"
        }
    """;

    public static final String ERROR_RESPONSE = """
        {
          "message": "Error inesperado. Intente más tarde.",
          "time": "2025-04-22T00:00:00"
        }
    """;

    // query parameters

    public static final String START_DATE_TIME_DESCRIPTION = "Fecha y hora de inicio de la visita (formato ISO)";
    public static final String START_DATE_TIME_EXAMPLE = "2025-05-01T10:00:00";

    public static final String END_DATE_TIME_DESCRIPTION = "Fecha y hora de fin de la visita (formato ISO)";
    public static final String END_DATE_TIME_EXAMPLE = "2025-05-01T11:00:00";

    public static final String CITY_ID_DESCRIPTION = "ID de la ciudad donde se encuentra el inmueble";
    public static final String CITY_ID_EXAMPLE = "1";

    public static final String SECTOR_DESCRIPTION = "Sector o barrio del inmueble";
    public static final String SECTOR_EXAMPLE = "Norte";

    public static final String PAGE_DESCRIPTION = "Número de página a mostrar (comienza en 0)";
    public static final String PAGE_EXAMPLE = "0";

    public static final String SIZE_DESCRIPTION = "Cantidad de visitas por página";
    public static final String SIZE_EXAMPLE = "10";

    public static final String SORT_BY_DESCRIPTION = "Campo por el cual ordenar los resultados";
    public static final String SORT_BY_VISIT_EXAMPLE = "startDateTime";

    public static final String SORT_DIRECTION_DESCRIPTION = "Dirección del ordenamiento (asc o desc)";
    public static final String SORT_DIRECTION_EXAMPLE = "asc";
}
