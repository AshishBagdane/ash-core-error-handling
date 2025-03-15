package com.ashishbagdane.lib.eh.exception.validation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;

import java.util.Map;
import java.util.Set;

/**
 * Exception thrown when input validation fails.
 */
public class InvalidInputException extends ValidationException {

    public InvalidInputException(String field, String value, String reason) {
        super(
            ErrorCode.VALIDATION_INVALID_PARAMETER,
            String.format("Invalid value '%s' for field '%s': %s", value, field, reason),
            createErrorContext()
                .attribute("field", field)
                .attribute("invalidValue", value)
                .attribute("reason", reason)
                .build()
        );
    }

    public InvalidInputException(Map<String, String> validationErrors) {
        super(
            ErrorCode.VALIDATION_INVALID_PARAMETER,
            "Multiple validation errors occurred",
            createErrorContext()
                .attribute("validationErrors", validationErrors)
                .attribute("errorCount", validationErrors.size())
                .build()
        );
    }

    public InvalidInputException(String field, Set<String> allowedValues, String actualValue) {
        super(
            ErrorCode.VALIDATION_INVALID_PARAMETER,
            String.format("Invalid value '%s' for field '%s'. Allowed values are: %s",
                          actualValue, field, String.join(", ", allowedValues)),
            createErrorContext()
                .attribute("field", field)
                .attribute("invalidValue", actualValue)
                .attribute("allowedValues", allowedValues)
                .build()
        );
    }
}
