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
}
