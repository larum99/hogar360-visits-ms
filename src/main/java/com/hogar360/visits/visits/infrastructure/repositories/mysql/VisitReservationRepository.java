package com.hogar360.visits.visits.infrastructure.repositories.mysql;

import com.hogar360.visits.visits.infrastructure.entities.VisitReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitReservationRepository extends JpaRepository<VisitReservationEntity, Long> {
    int countByVisitId(Long visitId);
    boolean existsByVisitIdAndBuyerEmail(Long visitId, String buyerEmail);
}

