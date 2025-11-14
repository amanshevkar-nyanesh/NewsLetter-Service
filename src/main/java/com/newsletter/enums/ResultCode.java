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

    
    private final String status;      // "S" for Success, "F" for Failure
    private final String code;        // Result code (e.g., "001", "101")
    private final String message;     // Human-readable message
    
    /**
     * Find ResultCode by code value
     */
    public static ResultCode findByCode(String code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }
    
    /**
     * Check if the result code represents success
     */
    public boolean isSuccess() {
        return "S".equals(this.status);
    }
    
    /**
     * Check if the result code represents failure
     */
    public boolean isFailure() {
        return "F".equals(this.status);
    }
}

