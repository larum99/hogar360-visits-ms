package com.hogar360.visits.visits.domain.utils.constants;

import java.util.Set;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_HOUSE_ID_NULL_MESSAGE = "The house ID cannot be null.";
    public static final String FIELD_START_DATE_NULL_MESSAGE = "The start date cannot be null.";
    public static final String FIELD_END_DATE_NULL_MESSAGE = "The end date cannot be null.";

    public static final String FIELD_VISIT_NULL_MESSAGE = "The visit schedule is required.";
    public static final String FIELD_VISIT_ID_NULL_MESSAGE = "The visit schedule ID is required.";
    public static final String FIELD_BUYER_EMAIL_NULL_MESSAGE = "The buyer email is required.";

    public static final String ROLE_SELLER = "VENDEDOR";

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_SIZE_NUMBER = 1;

    public static final int MAX_RESERVATIONS_PER_VISIT = 2;
    public static final int MAX_WEEKS_IN_ADVANCE_FOR_VISIT = 3;

    public static final Set<String> VALID_SORT_FIELDS_VISITS = Set.of(
            "startDateTime",
            "endDateTime",
            //"sector",
            "userId"
    );

    public static final String SORT_DIRECTION_ASC = "asc";
    public static final String SORT_DIRECTION_DESC = "desc";
}
