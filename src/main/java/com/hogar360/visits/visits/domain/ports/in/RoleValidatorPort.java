package com.hogar360.visits.visits.domain.ports.in;

public interface RoleValidatorPort {
    Long extractUserId(String token);
    String extractRole(String token);
}
