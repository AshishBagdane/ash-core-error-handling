package com.ashishbagdane.lib.eh.exception.validation.api;

import com.ashishbagdane.lib.eh.exception.validation.base.DefaultValidationResult;

import java.util.List;

/**
 * Represents the result of a validation operation. Contains information about whether the validation passed and any
 * validation errors encountered.
 *
 * @since 1.0
 */
public interface ValidationResult {

    /**
     * Checks if the validation was successful.
     *
     * @return true if validation passed with no errors, false otherwise
     */
    boolean isValid();

    /**
     * Convenience method to check if validation failed.
     *
     * @return true if validation failed (has errors), false otherwise
     */
    default boolean isInvalid() {
        return !isValid();
    }

    /**
     * Returns the list of validation errors if any were encountered.
     *
     * @return List of {@link ValidationError}. Empty list if validation was successful.
     */
    List<ValidationError> getErrors();

    /**
     * Creates a successful validation result with no errors.
     *
     * @return A valid {@link ValidationResult}
     */
    static ValidationResult valid() {
        return DefaultValidationResult.valid();
    }

    /**
     * Creates a failed validation result with the specified errors.
     *
     * @param errors List of validation errors
     * @return An invalid {@link ValidationResult} containing the specified errors
     */
    static ValidationResult invalid(List<ValidationError> errors) {
        return new DefaultValidationResult(false, errors);
    }
}
