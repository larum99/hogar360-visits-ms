package com.hogar360.visits.visits.infrastructure.adapters.persistence;

import com.hogar360.visits.visits.domain.model.VisitReservationModel;
import com.hogar360.visits.visits.domain.ports.out.VisitReservationPersistencePort;
import com.hogar360.visits.visits.infrastructure.repositories.mysql.VisitReservationRepository;
import com.hogar360.visits.visits.infrastructure.mappers.VisitReservationEntityMapper;
import com.hogar360.visits.visits.infrastructure.entities.VisitReservationEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitReservationPersistenceAdapter implements VisitReservationPersistencePort {

    private final VisitReservationRepository visitReservationRepository;
    private final VisitReservationEntityMapper visitReservationEntityMapper;

    @Override
    public void save(VisitReservationModel reservationModel) {
        VisitReservationEntity entity = visitReservationEntityMapper.modelToEntity(reservationModel);
        visitReservationRepository.save(entity);
    }

    @Override
    public int countByVisitId(Long visitId) {
        return visitReservationRepository.countByVisitId(visitId);
    }

    @Override
    public boolean existsByVisitIdAndBuyerEmail(Long visitId, String buyerEmail) {
        return visitReservationRepository.existsByVisitIdAndBuyerEmail(visitId, buyerEmail);
    }

    @Override
    public Optional<VisitReservationModel> findById(Long id) {
        VisitReservationEntity entity = visitReservationRepository.findById(id).orElse(null);
        VisitReservationModel model = visitReservationEntityMapper.entityToModel(entity);
        return Optional.ofNullable(model);
    }
}
