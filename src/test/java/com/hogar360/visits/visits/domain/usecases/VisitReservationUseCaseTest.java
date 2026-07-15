package com.hogar360.visits.visits.domain.usecases;

import com.hogar360.visits.visits.domain.exceptions.*;
import com.hogar360.visits.visits.domain.model.VisitModel;
import com.hogar360.visits.visits.domain.model.VisitReservationModel;
import com.hogar360.visits.visits.domain.ports.out.VisitPersistencePort;
import com.hogar360.visits.visits.domain.ports.out.VisitReservationPersistencePort;
import com.hogar360.visits.visits.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitReservationUseCaseTest {

    @Mock
    private VisitPersistencePort visitPersistencePort;

    @Mock
    private VisitReservationPersistencePort reservationPersistencePort;

    @InjectMocks
    private VisitReservationUseCase visitReservationUseCase;

    private VisitModel mockVisit;
    private VisitReservationModel validReservationModel;
    private Long visitId;
    private String buyerEmail;

    @BeforeEach
    void setUp() {
        visitId = 1L;
        buyerEmail = "buyer@example.com";
        
        mockVisit = new VisitModel(
                visitId,
                10L,
                100L,
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0)
        );

        validReservationModel = new VisitReservationModel(
                null,
                new VisitModel(visitId, null, null, null, null),
                buyerEmail,
                null
        );
    }

    @Test
    void reserveVisit_ValidReservation_ShouldSaveSuccessfully() {
        when(visitPersistencePort.findById(visitId)).thenReturn(Optional.of(mockVisit));
        when(reservationPersistencePort.countByVisitId(visitId)).thenReturn(1);
        when(reservationPersistencePort.existsByVisitIdAndBuyerEmail(visitId, buyerEmail)).thenReturn(false);

        visitReservationUseCase.reserveVisit(validReservationModel);

        ArgumentCaptor<VisitReservationModel> reservationCaptor = ArgumentCaptor.forClass(VisitReservationModel.class);
        verify(reservationPersistencePort, times(1)).save(reservationCaptor.capture());
        VisitReservationModel savedReservation = reservationCaptor.getValue();

        assertNotNull(savedReservation);
        assertEquals(mockVisit.getId(), savedReservation.getVisit().getId());
        assertEquals(buyerEmail, savedReservation.getBuyerEmail());
        assertNotNull(savedReservation.getCreatedAt());
        assertEquals(mockVisit, savedReservation.getVisit());

        verify(visitPersistencePort).findById(visitId);
        verify(reservationPersistencePort).countByVisitId(visitId);
        verify(reservationPersistencePort).existsByVisitIdAndBuyerEmail(visitId, buyerEmail);
    }

    @Test
    void reserveVisit_MissingVisit_ShouldThrowNullPointerException() {
        validReservationModel.setVisit(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        assertEquals(DomainConstants.FIELD_VISIT_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(reservationPersistencePort);
    }

    @Test
    void reserveVisit_MissingVisitId_ShouldThrowNullPointerException() {
        validReservationModel.getVisit().setId(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        assertEquals(DomainConstants.FIELD_VISIT_ID_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(reservationPersistencePort);
    }

    @Test
    void reserveVisit_MissingBuyerEmail_ShouldThrowNullPointerException() {
        validReservationModel.setBuyerEmail(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        assertEquals(DomainConstants.FIELD_BUYER_EMAIL_NULL_MESSAGE, exception.getMessage());
        verifyNoInteractions(visitPersistencePort);
        verifyNoInteractions(reservationPersistencePort);
    }

    @Test
    void reserveVisit_VisitNotFound_ShouldThrowVisitNotFoundException() {
        when(visitPersistencePort.findById(visitId)).thenReturn(Optional.empty());

        assertThrows(VisitNotFoundException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        verify(visitPersistencePort).findById(visitId);
        verifyNoInteractions(reservationPersistencePort);
    }

    @Test
    void reserveVisit_VisitAlreadyStarted_ShouldThrowVisitAlreadyStartedException() {
        mockVisit.setStartDateTime(LocalDateTime.now().minusHours(1));
        when(visitPersistencePort.findById(visitId)).thenReturn(Optional.of(mockVisit));

        assertThrows(VisitAlreadyStartedException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        verify(visitPersistencePort).findById(visitId);
        verifyNoMoreInteractions(visitPersistencePort);
        verifyNoInteractions(reservationPersistencePort);
    }

    @Test
    void reserveVisit_VisitFullyBooked_ShouldThrowVisitFullyBookedException() {
        when(visitPersistencePort.findById(visitId)).thenReturn(Optional.of(mockVisit));
        when(reservationPersistencePort.countByVisitId(visitId)).thenReturn(2);

        assertThrows(VisitFullyBookedException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        verify(visitPersistencePort).findById(visitId);
        verify(reservationPersistencePort).countByVisitId(visitId);
        verify(reservationPersistencePort, never()).existsByVisitIdAndBuyerEmail(anyLong(), anyString());
        verify(reservationPersistencePort, never()).save(any(VisitReservationModel.class));
    }

    @Test
    void reserveVisit_BuyerAlreadyBookedVisit_ShouldThrowBuyerAlreadyBookedVisitException() {
        when(visitPersistencePort.findById(visitId)).thenReturn(Optional.of(mockVisit));
        when(reservationPersistencePort.countByVisitId(visitId)).thenReturn(1);
        when(reservationPersistencePort.existsByVisitIdAndBuyerEmail(visitId, buyerEmail)).thenReturn(true); // Ya reservada

        assertThrows(BuyerAlreadyBookedVisitException.class,
                () -> visitReservationUseCase.reserveVisit(validReservationModel));

        verify(visitPersistencePort).findById(visitId);
        verify(reservationPersistencePort).countByVisitId(visitId);
        verify(reservationPersistencePort).existsByVisitIdAndBuyerEmail(visitId, buyerEmail);
        verify(reservationPersistencePort, never()).save(any(VisitReservationModel.class));
    }
}