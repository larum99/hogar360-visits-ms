package com.hogar360.visits.visits.infrastructure.adapters.persistence;

import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;

import com.hogar360.visits.visits.infrastructure.repositories.mysql.VisitRepository;
import com.hogar360.visits.visits.infrastructure.mappers.VisitEntityMapper;
import com.hogar360.visits.visits.infrastructure.entities.VisitEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitPersistenceAdapter implements VisitPersistencePort {

    private final VisitRepository visitRepository;
    private final VisitEntityMapper visitEntityMapper;

    @Override
    public void save(VisitModel visitModel) {
        VisitEntity visitEntity = visitEntityMapper.modelToEntity(visitModel);
        visitRepository.save(visitEntity);
    }

    @Override
    public boolean hasOverlappingVisits(Long sellerId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return visitRepository.hasOverlappingVisits(sellerId, startDateTime, endDateTime);
    }
}