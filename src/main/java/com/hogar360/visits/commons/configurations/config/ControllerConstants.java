package com.hogar360.visits.commons.configurations.config;

public class ControllerConstants {
    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BASE_URL_VISITS = "/api/v1/visits";
    public static final String BASE_URL_RESERVATIONS = "/api/v1/visits/reservations";
    public static final String PATH = "/";
    public static final String SEARCH_PATH = "/search";
    public static final String AVAILABLE_PATH = "/available/{houseId}";
    public static final String REST_ENDPOINT_PACKAGE = "com.hogar360.visits.visits.infrastructure.endpoints.rest";
    public static final String TAG_VISITS = "Visitas";
    public static final String TAG_DESCRIPTION_VISITS = "Operaciones relacionadas con la disponibilidad de visitas";
    public static final String TAG_RESERVATIONS = "Reservas";
    public static final String TAG_DESCRIPTION_RESERVATIONS = "Operaciones relacionadas con la reserva de visitas";
    public static final String ROLE_SELLER = "hasRole('VENDEDOR')";
    public static final String BEARER_PREFIX = "Bearer ";
}
