package com.ashishbagdane.lib.eh.exception.validation.validators;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;
import com.ashishbagdane.lib.eh.exception.validation.base.FieldValidator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Validates that a field is not null or empty.
 *
 * @param <T> The type of the object containing the field to validate
 * @param <F> The type of the field being validated
 */
@Component
public final class RequiredFieldValidator<T, F> extends FieldValidator<T, F> {

    /**
     * Creates a new RequiredFieldValidator.
     *
     * @param fieldExtractor Function to extract the field value from the object
     * @param fieldName      Name of the field being validated
     * @throws IllegalArgumentException if fieldExtractor or fieldName is null/empty
     */
    public RequiredFieldValidator(Function<T, F> fieldExtractor, String fieldName) {
        super(fieldExtractor, fieldName);
        validateConstructorParameters(fieldExtractor, fieldName);
    }

    private void validateConstructorParameters(Function<T, F> fieldExtractor, String fieldName) {
        if (fieldExtractor == null) {
            throw new IllegalArgumentException("Field extractor cannot be null");
        }
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }
    }

    @Override
    protected ValidationResult validateField(F field) {
        if (isEmptyField(field)) {
            return ValidationResult.invalid(Collections.singletonList(
                createError(
                    ErrorCode.VALIDATION_MISSING_FIELD,
                    String.format("Field '%s' is required", getFieldName())
                )
            ));
        }
        return ValidationResult.valid();
    }

    private boolean isEmptyField(F field) {
        if (field == null) {
            return true;
        }

        if (field instanceof CharSequence) {
            return ((CharSequence) field).toString().trim().isEmpty();
        }

        if (field instanceof Collection<?>) {
            return ((Collection<?>) field).isEmpty();
        }

        if (field instanceof Map<?, ?>) {
            return ((Map<?, ?>) field).isEmpty();
        }

        return false;
    }
}
