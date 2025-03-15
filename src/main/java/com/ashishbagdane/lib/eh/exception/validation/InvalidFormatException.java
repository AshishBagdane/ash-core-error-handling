package com.ashishbagdane.lib.eh.exception.validation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;

/**
 * Exception thrown when data format validation fails.
 */
public class InvalidFormatException extends ValidationException {

    public InvalidFormatException(String field, String value, String expectedFormat) {
        super(
            ErrorCode.VALIDATION_INVALID_FORMAT,
            String.format("Invalid format for field '%s': '%s'. Expected format: %s",
                          field, value, expectedFormat),
            createErrorContext()
                .attribute("field", field)
                .attribute("invalidValue", value)
                .attribute("expectedFormat", expectedFormat)
                .build()
        );
    }

    public InvalidFormatException(String field, String value, String expectedFormat, String example) {
        super(
            ErrorCode.VALIDATION_INVALID_FORMAT,
            String.format("Invalid format for field '%s': '%s'. Expected format: %s (Example: %s)",
                          field, value, expectedFormat, example),
            createErrorContext()
                .attribute("field", field)
                .attribute("invalidValue", value)
                .attribute("expectedFormat", expectedFormat)
                .attribute("example", example)
                .build()
        );
    }
}
