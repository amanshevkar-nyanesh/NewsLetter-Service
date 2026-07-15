package com.newsletter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for API result codes, messages, and statuses.
 * Used for standardizing API responses.
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    
    // Success codes
    SUCCESS("S", "001", "Success"),
    CREATED("S", "002", "Resource created successfully"),
    UPDATED("S", "003", "Resource updated successfully"),
    DELETED("S", "004", "Resource deleted successfully"),
    DATA_NOT_FOUND("S", "007", "No data found for given ID"),

    // Failure codes
    FAILURE("F", "101", "Operation failed");

    
    private final String status;
    private final String code;
    private final String message;

}

