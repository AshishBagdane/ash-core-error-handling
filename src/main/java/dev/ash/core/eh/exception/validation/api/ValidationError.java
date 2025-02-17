package dev.ash.core.eh.exception.validation.api;

import dev.ash.core.eh.core.ErrorCode;

import java.util.Map;

/**
 * Interface representing a validation error containing an error code, message, and optional metadata.
 */
public interface ValidationError {

    /**
     * Gets the error code identifying the type of validation failure.
     *
     * @return The {@link ErrorCode} representing the type of validation error
     */
    ErrorCode getErrorCode();

    /**
     * Gets the human-readable error message. This may be customized from the default message in {@link ErrorCode}.
     *
     * @return String containing the error message
     */
    String getMessage();

    /**
     * Gets additional metadata associated with the validation error.
     *
     * @return Unmodifiable Map containing error metadata
     */
    Map<String, Object> getMetadata();
}
