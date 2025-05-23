package com.hogar360.visits.visits.infrastructure.security;

import com.hogar360.visits.visits.domain.ports.in.RoleValidatorPort;
import com.hogar360.visits.visits.infrastructure.security.utils.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class JwtRoleValidator implements RoleValidatorPort {

    private final JwtUtil jwtUtil;

    public JwtRoleValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }

    @Override
    public Long extractUserId(String token) {
        return jwtUtil.extractUserId(token);
    }
}
