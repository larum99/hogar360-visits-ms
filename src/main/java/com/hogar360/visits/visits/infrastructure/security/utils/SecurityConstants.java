package com.hogar360.visits.visits.infrastructure.security.utils;

import org.springframework.http.HttpMethod;

import java.util.List;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ROLE_CLAIM = "role";
    public static final String ID_CLAIM = "id";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String ALLOWED_ORIGIN = "http://localhost:4200";
    public static final List<String> ALLOWED_METHODS = List.of(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
    );
    public static final List<String> ALLOWED_HEADERS = List.of("*");

    public static final List<String> PUBLIC_PATHS = List.of(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**",
            "/api-docs",
            "/api/v1/visit/**",
            "/api/v1/visit/search",
            "/api/v1/role/validate",
            "/api/v1/user/login"
    );

    public static final String VISIT_PROTECTED_PATH = "/api/v1/visit/";

}
