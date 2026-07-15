package com.hogar360.visits.visits.infrastructure.exceptionhandlers;

public class ExceptionConstants {
    private ExceptionConstants() {}

    public static final String VISIT_OVERLAP_EXCEPTION_MESSAGE = "There is already a scheduled visit that overlaps with the requested time.";
    public static final String HOUSE_NOT_FOUND_EXCEPTION_MESSAGE = "The house was not found.";
    public static final String USER_NOT_HOUSE_OWNER_EXCEPTION_MESSAGE = "You are not the owner of the house.";
    public static final String START_DATE_BEFORE_NOW_EXCEPTION_MESSAGE = "The start date must be in the future.";
    public static final String END_DATE_BEFORE_START_EXCEPTION_MESSAGE = "The end date must be after the start date.";
    public static final String DATE_OUT_OF_RANGE_EXCEPTION_MESSAGE = "The visit must be scheduled within the next 3 weeks.";
    public static final String INVALID_VISIT_DURATION_EXCEPTION_MESSAGE = "The visit duration must be greater than 0.";
    public static final String FORBIDDEN_EXCEPTION_MESSAGE = "You do not have permission to perform this action.";
    public static final String BUYER_ALREADY_BOOKED_EXCEPTION_MESSAGE = "You have already booked this visit.";
    public static final String VISIT_ALREADY_STARTED_EXCEPTION_MESSAGE = "The visit has already started and cannot be modified.";
    public static final String VISIT_FULLY_BOOKED_EXCEPTION_MESSAGE = "The visit is fully booked.";
    public static final String VISIT_NOT_FOUND_EXCEPTION_MESSAGE = "The visit was not found.";
    public static final String PAGE_SIZE_INVALID_EXCEPTION_MESSAGE = "The page size provided is invalid.";
    public static final String PAGE_NUMBER_NEGATIVE_EXCEPTION_MESSAGE = "The page number must be zero or greater.";
    public static final String INVALID_SORT_DIRECTION_EXCEPTION_MESSAGE = "The sort direction is invalid. Use 'asc' or 'desc'.";
    public static final String INVALID_SORT_FIELD_EXCEPTION_MESSAGE = "The sort field provided is invalid.";
    public static final String INVALID_DATE_RANGE_EXCEPTION_MESSAGE = "The specified date range is invalid.";
}
