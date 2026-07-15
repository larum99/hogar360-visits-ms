package com.hogar360.visits.visits.infrastructure.exceptionhandlers;

import com.hogar360.visits.visits.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(VisitOverlapException.class)
    public ResponseEntity<ExceptionResponse> handleVisitOverlapException(VisitOverlapException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.VISIT_OVERLAP_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(HouseNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleHouseNotFoundException(HouseNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.HOUSE_NOT_FOUND_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UserIsNotHouseOwnerException.class)
    public ResponseEntity<ExceptionResponse> handleUserIsNotHouseOwnerException(UserIsNotHouseOwnerException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.USER_NOT_HOUSE_OWNER_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(StartDateBeforeNowException.class)
    public ResponseEntity<ExceptionResponse> handleStartDateBeforeNowException(StartDateBeforeNowException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.START_DATE_BEFORE_NOW_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ResponseEntity<ExceptionResponse> handleEndDateBeforeStartDateException(EndDateBeforeStartDateException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.END_DATE_BEFORE_START_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(DateOutOfRangeException.class)
    public ResponseEntity<ExceptionResponse> handleDateOutOfRangeException(DateOutOfRangeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.DATE_OUT_OF_RANGE_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidVisitDurationException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidVisitDurationException(InvalidVisitDurationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_VISIT_DURATION_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.FORBIDDEN_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(BuyerAlreadyBookedVisitException.class)
    public ResponseEntity<ExceptionResponse> handleBuyerAlreadyBookedVisitException(BuyerAlreadyBookedVisitException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.BUYER_ALREADY_BOOKED_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(VisitAlreadyStartedException.class)
    public ResponseEntity<ExceptionResponse> handleVisitAlreadyStartedException(VisitAlreadyStartedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.VISIT_ALREADY_STARTED_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(VisitFullyBookedException.class)
    public ResponseEntity<ExceptionResponse> handleVisitFullyBookedException(VisitFullyBookedException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.VISIT_FULLY_BOOKED_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleVisitNotFoundException(VisitNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ExceptionConstants.VISIT_NOT_FOUND_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(PageSizeInvalidException.class)
    public ResponseEntity<ExceptionResponse> handlePageSizeInvalidException(PageSizeInvalidException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.PAGE_SIZE_INVALID_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(PageNumberNegativeException.class)
    public ResponseEntity<ExceptionResponse> handlePageNumberNegativeException(PageNumberNegativeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.PAGE_NUMBER_NEGATIVE_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSortDirectionException(InvalidSortDirectionException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_SORT_DIRECTION_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidSortFieldException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSortFieldException(InvalidSortFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_SORT_FIELD_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDateRangeException(InvalidDateRangeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_DATE_RANGE_EXCEPTION_MESSAGE, LocalDateTime.now()));
    }

}
