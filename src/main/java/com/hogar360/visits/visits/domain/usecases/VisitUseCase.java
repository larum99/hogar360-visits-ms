package com.hogar360.visits.visits.domain.usecases;

import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.exceptions.*;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.ports.in.VisitServicePort;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.domain.utils.constants.DomainConstants;
import com.hogar360.visits.visits.domain.utils.PageResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VisitUseCase implements VisitServicePort {

    private final VisitPersistencePort visitPersistencePort;
    private final HouseServicePort houseServicePort;

    public VisitUseCase(VisitPersistencePort visitPersistencePort,
                        HouseServicePort houseServicePort) {
        this.visitPersistencePort = visitPersistencePort;
        this.houseServicePort = houseServicePort;
    }

    @Override
    public void saveVisit(VisitModel visitModel, Long sellerId, String role) {

        validateRole(role);
        validateMandatoryFields(visitModel);
        validateDates(visitModel.getStartDateTime(), visitModel.getEndDateTime());

        boolean hasOverlap = visitPersistencePort.hasOverlappingVisits(sellerId, visitModel.getStartDateTime(), visitModel.getEndDateTime());
        if (hasOverlap) {
            throw new VisitOverlapException();
        }

        Optional<Long> ownerIdOptional = houseServicePort.getOwnerId(visitModel.getHouseId());

        if (ownerIdOptional.isEmpty()) {
            throw new HouseNotFoundException();
        }

        Long ownerId = ownerIdOptional.get();

        if (!ownerId.equals(sellerId)) {
            throw new UserIsNotHouseOwnerException();
        }

        visitModel.setUserId(sellerId);

        visitPersistencePort.save(visitModel);
    }

    @Override
    public PageResult<VisitModel> listVisits(VisitCriteria criteria) {
        validatePage(criteria.getPage(), criteria.getSize());
        validateCriteria(criteria);
        return visitPersistencePort.list(criteria);
    }

    private void validateMandatoryFields(VisitModel visitModel) {
        Objects.requireNonNull(visitModel.getHouseId(), DomainConstants.FIELD_HOUSE_ID_NULL_MESSAGE);
        Objects.requireNonNull(visitModel.getStartDateTime(), DomainConstants.FIELD_START_DATE_NULL_MESSAGE);
        Objects.requireNonNull(visitModel.getEndDateTime(), DomainConstants.FIELD_END_DATE_NULL_MESSAGE);
    }

    private void validateDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeWeeksLater = now.plusWeeks(DomainConstants.MAX_WEEKS_IN_ADVANCE_FOR_VISIT);

        if (startDateTime.isBefore(now)) {
            throw new StartDateBeforeNowException();
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new EndDateBeforeStartDateException();
        }
        if (startDateTime.isAfter(threeWeeksLater) || endDateTime.isAfter(threeWeeksLater)) {
            throw new DateOutOfRangeException();
        }
        if (startDateTime.isEqual(endDateTime) || endDateTime.isBefore(startDateTime)) {
            throw new InvalidVisitDurationException();
        }
    }

    private void validateRole(String role) {
        if (!DomainConstants.ROLE_SELLER.equals(role)) {
            throw new ForbiddenException();
        }
    }

    private void validatePage(int page, int size) {
        validatePageNumber(page);
        validatePageSize(size);
    }

    private void validatePageNumber(int page) {
        if (page < DomainConstants.DEFAULT_PAGE_NUMBER) {
            throw new PageNumberNegativeException();
        }
    }

    private void validatePageSize(int size) {
        if (size <= DomainConstants.DEFAULT_SIZE_NUMBER) {
            throw new PageSizeInvalidException();
        }
    }

    private void validateCriteria(VisitCriteria criteria) {
        if (criteria.getStartDateTime() != null && criteria.getEndDateTime() != null) {
            if (criteria.getEndDateTime().isBefore(criteria.getStartDateTime())) {
                throw new InvalidDateRangeException();
            }
        }
        validateSortBy(criteria.getSortBy());

        if (criteria.getSortDirection() != null &&
                !criteria.getSortDirection().equalsIgnoreCase(DomainConstants.SORT_DIRECTION_ASC) &&
                !criteria.getSortDirection().equalsIgnoreCase(DomainConstants.SORT_DIRECTION_DESC)) {
            throw new InvalidSortDirectionException();
        }
    }

    private void validateSortBy(String sortBy) {
        if (sortBy == null) return;

        if (!DomainConstants.VALID_SORT_FIELDS_VISITS.contains(sortBy)) {
            throw new InvalidSortFieldException();
        }
    }

    @Override
    public List<VisitModel> getVisitsByLocation(Long cityId, String sector) {
        List<Long> houseIds = houseServicePort.getHouseIdsByLocation(cityId, sector);

        if (houseIds.isEmpty()) {
            return List.of();
        }

        return visitPersistencePort.findByHouseIds(houseIds);
    }

    @Override
    public List<VisitModel> getAvailableVisitsByHouseId(Long houseId) {
        return visitPersistencePort.findAvailableVisitsByHouseId(houseId);
    }
}