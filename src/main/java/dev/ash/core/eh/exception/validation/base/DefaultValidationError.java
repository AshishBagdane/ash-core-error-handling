package dev.ash.core.eh.exception.validation.base;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.exception.validation.api.ValidationError;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Default implementation of ValidationError interface. Provides immutable error information with optional metadata.
 */
public class DefaultValidationError implements ValidationError {

    private final ErrorCode errorCode;

    private final String message;

    private final Map<String, Object> metadata;

    /**
     * Creates a validation error with the specified error code and default message.
     *
     * @param errorCode the error code
     */
    public DefaultValidationError(ErrorCode errorCode) {
        this(errorCode, errorCode.getDefaultMessage(), Collections.emptyMap());
    }

    /**
     * Creates a validation error with the specified error code and custom message.
     *
     * @param errorCode the error code
     * @param message   custom error message
     */
    public DefaultValidationError(ErrorCode errorCode, String message) {
        this(errorCode, message, Collections.emptyMap());
    }

    /**
     * Creates a validation error with error code, custom message, and metadata.
     *
     * @param errorCode the error code
     * @param message   custom error message
     * @param metadata  additional error context
     */
    public DefaultValidationError(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
        this.metadata = Map.copyOf(metadata);
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return String.format("ValidationError{code=%d, message='%s', metadata=%s}",
                             errorCode.getCode(), message, metadata);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultValidationError that)) {
            return false;
        }
        return errorCode == that.errorCode &&
            Objects.equals(message, that.message) &&
            Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, message, metadata);
    }
}
