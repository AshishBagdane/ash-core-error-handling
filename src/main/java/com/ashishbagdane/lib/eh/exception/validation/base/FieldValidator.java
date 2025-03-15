package com.ashishbagdane.lib.eh.exception.validation.base;

import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;

import java.util.function.Function;

/**
 * Abstract validator for validating specific fields within an object. Provides field extraction and naming
 * functionality.
 *
 * @param <T> The type of object containing the field to validate
 * @param <F> The type of the field to validate
 * @since 1.0
 */
public abstract class FieldValidator<T, F> extends BaseValidator<T> {

    private final Function<T, F> fieldExtractor;

    private final String fieldName;

    protected FieldValidator(Function<T, F> fieldExtractor, String fieldName) {
        this.fieldExtractor = fieldExtractor;
        this.fieldName = fieldName;
    }

    protected abstract ValidationResult validateField(F field);

    @Override
    public ValidationResult validate(T input) {
        return validateField(fieldExtractor.apply(input));
    }

    protected String getFieldName() {
        return fieldName;
    }
}
