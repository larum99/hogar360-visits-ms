package com.hogar360.visits.visits.domain.usecases;

import com.hogar360.visits.visits.domain.criteria.VisitCriteria;
import com.hogar360.visits.visits.domain.exceptions.*;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.ports.out.HouseServicePort;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.utils.constants.DomainConstants;
import com.hogar360.visits.visits.domain.utils.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitUseCaseTest {

    @Mock
    private VisitPersistencePort visitPersistencePort;

    @Mock
    private HouseServicePort houseServicePort;

    @InjectMocks
    private VisitUseCase visitUseCase;

    private VisitModel validVisitModel;
    private Long sellerId;
    private String roleSeller;
    private String roleUser;

    @BeforeEach
    void setUp() {
        sellerId = 1L;
        roleSeller = DomainConstants.ROLE_SELLER;
        roleUser = "BUYER";

        validVisitModel = new VisitModel(
                null,
                null,
                100L,
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0)
        );

        try {
            java.lang.reflect.Field field = DomainConstants.class.getDeclaredField("VALID_SORT_FIELDS_VISITS");
            field.setAccessible(true);
            Set<String> validSortFields = (Set<String>) field.get(null);
            if (validSortFields.isEmpty()) {
                validSortFields.add("id");
                validSortFields.add("startDateTime");
                validSortFields.add("endDateTime");
                validSortFields.add("houseId");
                validSortFields.add("userId");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Could not access VALID_SORT_FIELDS_VISITS for test setup: " + e.getMessage());
        }
    }

    @Test
    void saveVisit_ValidVisit_ShouldSaveSuccessfully() {
        when(visitPersistencePort.hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime()))
                .thenReturn(false);
        when(houseServicePort.getOwnerId(validVisitModel.getHouseId())).thenReturn(Optional.of(sellerId));

        visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller);

        ArgumentCaptor<VisitModel> visitCaptor = ArgumentCaptor.forClass(VisitModel.class);
        verify(visitPersistencePort, times(1)).save(visitCaptor.capture());
        VisitModel savedVisit = visitCaptor.getValue();

        assertNotNull(savedVisit);
        assertEquals(sellerId, savedVisit.getUserId());
        verify(visitPersistencePort).hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime());
        verify(houseServicePort).getOwnerId(validVisitModel.getHouseId());
    }

    @Test
    void saveVisit_NonSellerRole_ShouldThrowForbiddenException() {
        assertThrows(ForbiddenException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleUser));

        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_MissingHouseId_ShouldThrowNullPointerException() {
        validVisitModel.setHouseId(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        assertEquals(DomainConstants.FIELD_HOUSE_ID_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_MissingStartDateTime_ShouldThrowNullPointerException() {
        validVisitModel.setStartDateTime(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        assertEquals(DomainConstants.FIELD_START_DATE_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_MissingEndDateTime_ShouldThrowNullPointerException() {
        validVisitModel.setEndDateTime(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        assertEquals(DomainConstants.FIELD_END_DATE_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_StartDateBeforeNow_ShouldThrowStartDateBeforeNowException() {
        validVisitModel.setStartDateTime(LocalDateTime.now().minusDays(1));

        assertThrows(StartDateBeforeNowException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_EndDateBeforeStartDate_ShouldThrowEndDateBeforeStartDateException() {
        validVisitModel.setStartDateTime(LocalDateTime.now().plusDays(2));
        validVisitModel.setEndDateTime(LocalDateTime.now().plusDays(1));

        assertThrows(EndDateBeforeStartDateException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_VisitDateTooFarInFuture_ShouldThrowDateOutOfRangeException() {
        validVisitModel.setStartDateTime(LocalDateTime.now().plusWeeks(4));
        validVisitModel.setEndDateTime(LocalDateTime.now().plusWeeks(4).plusHours(1));

        assertThrows(DateOutOfRangeException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_StartDateEqualsEndDate_ShouldThrowInvalidVisitDurationException() {
        LocalDateTime sameTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
        validVisitModel.setStartDateTime(sameTime);
        validVisitModel.setEndDateTime(sameTime);

        assertThrows(InvalidVisitDurationException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(houseServicePort);
    }

    @Test
    void saveVisit_OverlappingVisits_ShouldThrowVisitOverlapException() {
        when(visitPersistencePort.hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime()))
                .thenReturn(true);

        assertThrows(VisitOverlapException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verify(visitPersistencePort).hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime());
        verifyNoInteractions(houseServicePort);
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void saveVisit_HouseNotFound_ShouldThrowHouseNotFoundException() {
        when(visitPersistencePort.hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime()))
                .thenReturn(false);
        when(houseServicePort.getOwnerId(validVisitModel.getHouseId())).thenReturn(Optional.empty());

        assertThrows(HouseNotFoundException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verify(visitPersistencePort).hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime());
        verify(houseServicePort).getOwnerId(validVisitModel.getHouseId());
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void saveVisit_UserIsNotHouseOwner_ShouldThrowUserIsNotHouseOwnerException() {
        Long otherOwnerId = 2L;
        when(visitPersistencePort.hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime()))
                .thenReturn(false);
        when(houseServicePort.getOwnerId(validVisitModel.getHouseId())).thenReturn(Optional.of(otherOwnerId));

        assertThrows(UserIsNotHouseOwnerException.class,
                () -> visitUseCase.saveVisit(validVisitModel, sellerId, roleSeller));

        verify(visitPersistencePort).hasOverlappingVisits(sellerId, validVisitModel.getStartDateTime(), validVisitModel.getEndDateTime());
        verify(houseServicePort).getOwnerId(validVisitModel.getHouseId());
        verify(visitPersistencePort, never()).save(any(VisitModel.class));
    }

    @Test
    void listVisits_ValidCriteria_ShouldReturnPageResult() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                1,
                10,
                null,
                null,
                null
        );
        PageResult<VisitModel> expectedPageResult = new PageResult<>(
                Collections.singletonList(validVisitModel),
                1L,
                1,
                1,
                10,
                true,
                true
        );
        when(visitPersistencePort.list(criteria)).thenReturn(expectedPageResult);

        PageResult<VisitModel> actualPageResult = visitUseCase.listVisits(criteria);

        assertNotNull(actualPageResult);
        assertEquals(expectedPageResult.getContent().size(), actualPageResult.getContent().size());
        assertEquals(expectedPageResult.getTotalElements(), actualPageResult.getTotalElements());
        verify(visitPersistencePort).list(criteria);
    }

    @Test
    void listVisits_NegativePageNumber_ShouldThrowPageNumberNegativeException() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                -1,
                10,
                null,
                null,
                null
        );

        assertThrows(PageNumberNegativeException.class,
                () -> visitUseCase.listVisits(criteria));

        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void listVisits_InvalidPageSize_ShouldThrowPageSizeInvalidException() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                0,
                0,
                null,
                null,
                null
        );

        assertThrows(PageSizeInvalidException.class,
                () -> visitUseCase.listVisits(criteria));

        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void listVisits_CriteriaEndDateBeforeStartDate_ShouldThrowInvalidDateRangeException() {
        VisitCriteria criteria = new VisitCriteria(
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(1),
                null,
                null,
                null,
                0,
                10,
                null,
                null,
                null
        );

        assertThrows(InvalidDateRangeException.class,
                () -> visitUseCase.listVisits(criteria));

        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void listVisits_InvalidSortDirection_ShouldThrowInvalidSortDirectionException() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "startDateTime",
                "INVALID_DIR",
                null
        );

        assertThrows(InvalidSortDirectionException.class,
                () -> visitUseCase.listVisits(criteria));

        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void listVisits_InvalidSortField_ShouldThrowInvalidSortFieldException() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "invalidField",
                "asc",
                null
        );

        assertThrows(InvalidSortFieldException.class,
                () -> visitUseCase.listVisits(criteria));

        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void listVisits_ValidSortFieldAndDirection_ShouldCallPersistence() {
        VisitCriteria criteria = new VisitCriteria(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "startDateTime",
                "asc",
                null
        );
        PageResult<VisitModel> expectedPageResult = new PageResult<>(
                Collections.singletonList(validVisitModel),
                1L,
                1,
                1,
                10,
                true,
                true
        );
        when(visitPersistencePort.list(criteria)).thenReturn(expectedPageResult);

        PageResult<VisitModel> actualPageResult = visitUseCase.listVisits(criteria);

        assertNotNull(actualPageResult);
        verify(visitPersistencePort).list(criteria);
    }

    @Test
    void getVisitsByLocation_HousesFound_ShouldReturnVisits() {
        Long cityId = 1L;
        String sector = "Centro";
        List<Long> houseIds = Arrays.asList(100L, 101L);
        List<VisitModel> expectedVisits = Arrays.asList(
                new VisitModel(1L, 1L, 100L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1)),
                new VisitModel(2L, 1L, 101L, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1))
        );

        when(houseServicePort.getHouseIdsByLocation(cityId, sector)).thenReturn(houseIds);
        when(visitPersistencePort.findByHouseIds(houseIds)).thenReturn(expectedVisits);

        List<VisitModel> actualVisits = visitUseCase.getVisitsByLocation(cityId, sector);

        assertNotNull(actualVisits);
        assertFalse(actualVisits.isEmpty());
        assertEquals(2, actualVisits.size());
        verify(houseServicePort).getHouseIdsByLocation(cityId, sector);
        verify(visitPersistencePort).findByHouseIds(houseIds);
    }

    @Test
    void getVisitsByLocation_NoHousesFound_ShouldReturnEmptyList() {
        Long cityId = 1L;
        String sector = "Norte";

        when(houseServicePort.getHouseIdsByLocation(cityId, sector)).thenReturn(Collections.emptyList());

        List<VisitModel> actualVisits = visitUseCase.getVisitsByLocation(cityId, sector);

        assertNotNull(actualVisits);
        assertTrue(actualVisits.isEmpty());
        verify(houseServicePort).getHouseIdsByLocation(cityId, sector);
        verifyNoInteractions(visitPersistencePort);
    }

    @Test
    void getAvailableVisitsByHouseId_VisitsFound_ShouldReturnVisits() {
        Long houseId = 100L;
        List<VisitModel> expectedVisits = Arrays.asList(
                new VisitModel(1L, 1L, houseId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1)),
                new VisitModel(2L, 1L, houseId, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1))
        );

        when(visitPersistencePort.findAvailableVisitsByHouseId(houseId)).thenReturn(expectedVisits);

        List<VisitModel> actualVisits = visitUseCase.getAvailableVisitsByHouseId(houseId);

        assertNotNull(actualVisits);
        assertFalse(actualVisits.isEmpty());
        assertEquals(2, actualVisits.size());
        verify(visitPersistencePort).findAvailableVisitsByHouseId(houseId);
    }

    @Test
    void getAvailableVisitsByHouseId_NoVisitsFound_ShouldReturnEmptyList() {
        Long houseId = 999L;

        when(visitPersistencePort.findAvailableVisitsByHouseId(houseId)).thenReturn(Collections.emptyList());

        List<VisitModel> actualVisits = visitUseCase.getAvailableVisitsByHouseId(houseId);

        assertNotNull(actualVisits);
        assertTrue(actualVisits.isEmpty());
        verify(visitPersistencePort).findAvailableVisitsByHouseId(houseId);
    }
}