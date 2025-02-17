package dev.ash.core.eh.exception.resource;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.core.ErrorContext;
import dev.ash.core.eh.exception.base.AbstractApplicationException;

/**
 * Base class for resource-related exceptions in the application.
 */
public abstract class ResourceException extends AbstractApplicationException {

    protected ResourceException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected ResourceException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected ResourceException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
