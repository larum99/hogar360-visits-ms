package com.hogar360.visits.visits.infrastructure.adapters.persistence;

import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.utils.PageResult;
import com.hogar360.visits.visits.infrastructure.entities.VisitEntity;
import com.hogar360.visits.visits.infrastructure.mappers.VisitEntityMapper;
import com.hogar360.visits.visits.infrastructure.repositories.mysql.VisitRepository;
import com.hogar360.visits.visits.infrastructure.specifications.VisitSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<VisitModel> findById(Long id) {
        VisitEntity entity = visitRepository.findById(id).orElse(null);
        VisitModel model = visitEntityMapper.entityToModel(entity);
        return Optional.ofNullable(model);
    }

    @Override
    public PageResult<VisitModel> list(VisitCriteria criteria) {
        Sort sort = Sort.by(
                criteria.getSortDirection() != null && criteria.getSortDirection().equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                criteria.getSortBy() != null ? criteria.getSortBy() : "startDateTime"
        );
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        Specification<VisitEntity> specification = Specification.where(null);

        if (criteria.getHouseIds() != null) {
            if (criteria.getHouseIds().isEmpty()) {
                return new PageResult<>(List.of(), 0, 0, 0, 0, true, true);
            } else {
                specification = specification.and(VisitSpecification.hasHouseIdIn(criteria.getHouseIds()));
            }
        }

        if (criteria.getStartDateTime() != null) {
            specification = specification.and(VisitSpecification.hasStartDateTimeAfter(criteria.getStartDateTime()));
        }
        if (criteria.getEndDateTime() != null) {
            specification = specification.and(VisitSpecification.hasEndDateTimeBefore(criteria.getEndDateTime()));
        }

        Page<VisitEntity> page = visitRepository.findAll(specification, pageable);

        List<VisitModel> visitModels = visitEntityMapper.entityListToModelList(page.getContent());

        return new PageResult<>(
                visitModels,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    public List<VisitModel> findByHouseIds(List<Long> houseIds) {
        return visitRepository.findByHouseIdIn(houseIds)
                .stream()
                .map(visitEntityMapper::entityToModel)
                .toList();
    }

    @Override
    public List<VisitModel> findAvailableVisitsByHouseId(Long houseId) {
        return visitRepository.findAvailableVisitsByHouseId(houseId)
                .stream()
                .map(visitEntityMapper::entityToModel)
                .toList();
    }
}