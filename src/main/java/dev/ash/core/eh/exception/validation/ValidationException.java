package dev.ash.core.eh.exception.validation;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.core.ErrorContext;
import dev.ash.core.eh.exception.base.AbstractApplicationException;

/**
 * Base class for validation-related exceptions in the application.
 */
public abstract class ValidationException extends AbstractApplicationException {

    protected ValidationException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected ValidationException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected ValidationException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
