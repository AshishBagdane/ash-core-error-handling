package com.ashishbagdane.lib.eh.exception.validation.base;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationError;
import com.ashishbagdane.lib.eh.exception.validation.api.Validator;

import java.util.Map;

/**
 * Base implementation of the Validator interface providing common functionality for concrete validators.
 *
 * @param <T> The type of object to be validated
 */
public abstract class BaseValidator<T> implements Validator<T> {

    /**
     * Creates a validation error with the specified error code using its default message.
     *
     * @param errorCode the error code
     * @return A new {@link ValidationError}
     */
    protected ValidationError createError(ErrorCode errorCode) {
        return new DefaultValidationError(errorCode);
    }

    /**
     * Creates a validation error with the specified error code and custom message.
     *
     * @param errorCode the error code
     * @param message   custom error message
     * @return A new {@link ValidationError}
     */
    protected ValidationError createError(ErrorCode errorCode, String message) {
        return new DefaultValidationError(errorCode, message);
    }

    /**
     * Creates a validation error with error code, custom message, and metadata.
     *
     * @param errorCode the error code
     * @param message   custom error message
     * @param metadata  additional error context
     * @return A new {@link ValidationError}
     */
    protected ValidationError createError(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        return new DefaultValidationError(errorCode, message, metadata);
    }
}
