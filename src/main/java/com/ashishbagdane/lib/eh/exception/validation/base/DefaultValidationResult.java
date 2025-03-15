package com.ashishbagdane.lib.eh.exception.validation.base;

import com.ashishbagdane.lib.eh.exception.validation.api.ValidationError;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link ValidationResult} interface. Provides an immutable container for validation status
 * and errors.
 *
 * <p>This class is used to create both successful validation results (with no errors)
 * and failure results (containing validation errors). It provides static factory methods for convenient creation of
 * common validation results.</p>
 *
 * @see ValidationResult
 * @see ValidationError
 * @since 1.0
 */
public class DefaultValidationResult implements ValidationResult {

    private final boolean valid;

    private final List<ValidationError> errors;

    /**
     * Creates a new validation result with the specified status and errors.
     *
     * @param valid  the validation status
     * @param errors the list of validation errors (may be empty)
     * @throws NullPointerException if errors list is null
     */
    public DefaultValidationResult(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = Collections.unmodifiableList(Objects.requireNonNull(errors, "errors must not be null"));
    }

    /**
     * Creates a successful validation result with no errors.
     *
     * @return a valid {@link ValidationResult}
     */
    public static ValidationResult valid() {
        return new DefaultValidationResult(true, Collections.emptyList());
    }

    /**
     * Creates a failed validation result with the specified errors.
     *
     * @param errors the list of validation errors
     * @return an invalid {@link ValidationResult} containing the specified errors
     * @throws NullPointerException if errors list is null
     */
    public static ValidationResult invalid(List<ValidationError> errors) {
        return new DefaultValidationResult(false, errors);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public List<ValidationError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return String.format("ValidationResult{valid=%s, errors=%s}", valid, errors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultValidationResult that)) {
            return false;
        }
        return valid == that.valid && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, errors);
    }
}
