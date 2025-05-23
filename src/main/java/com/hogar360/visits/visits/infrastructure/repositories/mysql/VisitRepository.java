package com.hogar360.visits.visits.infrastructure.repositories.mysql;

import com.hogar360.visits.visits.infrastructure.entities.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long>, JpaSpecificationExecutor<VisitEntity> {

    @Query("SELECT COUNT(v) > 0 FROM VisitEntity v WHERE v.userId = :sellerId AND ((v.startDateTime < :endDateTime AND v.endDateTime > :startDateTime))")
    boolean hasOverlappingVisits(@Param("sellerId") Long sellerId,
                                 @Param("startDateTime") LocalDateTime startDateTime,
                                 @Param("endDateTime") LocalDateTime endDateTime);

}