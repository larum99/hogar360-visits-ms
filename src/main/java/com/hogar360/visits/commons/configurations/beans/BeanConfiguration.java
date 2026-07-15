package com.hogar360.visits.commons.configurations.beans;

import com.hogar360.visits.visits.domain.ports.in.VisitReservationServicePort;
import com.hogar360.visits.visits.domain.ports.in.VisitServicePort;
import com.hogar360.visits.visits.domain.ports.in.RoleValidatorPort;
import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.ports.out.VisitReservationPersistencePort;
import com.hogar360.visits.visits.domain.usecases.VisitReservationUseCase;
import com.hogar360.visits.visits.domain.usecases.VisitUseCase;
import com.hogar360.visits.visits.infrastructure.adapters.persistence.VisitPersistenceAdapter;
import com.hogar360.visits.visits.infrastructure.adapters.persistence.VisitReservationPersistenceAdapter;
import com.hogar360.visits.visits.infrastructure.mappers.VisitEntityMapper;
import com.hogar360.visits.visits.infrastructure.mappers.VisitReservationEntityMapper;
import com.hogar360.visits.visits.infrastructure.repositories.mysql.VisitRepository;
import com.hogar360.visits.visits.infrastructure.repositories.mysql.VisitReservationRepository;
import com.hogar360.visits.visits.infrastructure.security.JwtRoleValidator;
import com.hogar360.visits.visits.infrastructure.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final VisitRepository visitRepository;
    private final VisitEntityMapper visitEntityMapper;
    private final HouseServicePort houseServicePort;
    private final JwtUtil jwtUtil;
    private final VisitReservationRepository visitReservationRepository;
    private final VisitReservationEntityMapper visitReservationEntityMapper;

    @Bean
    public VisitServicePort visitServicePort(RoleValidatorPort roleValidatorPort) {
        return new VisitUseCase(visitPersistencePort(), houseServicePort);
    }

    @Bean
    public VisitPersistencePort visitPersistencePort() {
        return new VisitPersistenceAdapter(visitRepository, visitEntityMapper);
    }

    @Bean
    public RoleValidatorPort roleValidatorPort() {
        return new JwtRoleValidator(jwtUtil);
    }

    @Bean
    public VisitReservationPersistencePort visitReservationPersistencePort() {
        return new VisitReservationPersistenceAdapter(visitReservationRepository, visitReservationEntityMapper);
    }

    @Bean
    public VisitReservationServicePort visitReservationServicePort() {
        return new VisitReservationUseCase(visitPersistencePort(), visitReservationPersistencePort());
    }
}
