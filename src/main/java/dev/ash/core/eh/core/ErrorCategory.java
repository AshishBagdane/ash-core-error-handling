package dev.ash.core.eh.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Defines the main categories of errors in the application.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCategory {
    HTTP_STANDARD("HTTP", "HTTP Standard Error", 400, 599),      // Standard HTTP status codes
    VALIDATION("VAL", "Validation Error", 1000, 1999),          // 1xxx range for validation
    BUSINESS("BUS", "Business Error", 2000, 2999),              // 2xxx range for business
    SECURITY("SEC", "Security Error", 3000, 3999),              // 3xxx range for security
    DATA("DATA", "Data Error", 4000, 4999),                     // 4xxx range for data
    INTEGRATION("INT", "Integration Error", 5000, 5999),        // 5xxx range for integration
    SYSTEM("SYS", "System Error", 9000, 9999);                  // 9xxx range for system

    private final String code;

    private final String description;

    private final int minCode;

    private final int maxCode;

    /**
     * Checks if the given error code falls within this category's range.
     *
     * @param code The error code to check
     * @return true if the code is within this category's range, false otherwise
     */
    public boolean contains(int code) {
        return code >= minCode && code <= maxCode;
    }
}
