package dev.ash.core.eh.exception.operation;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.core.ErrorContext;
import dev.ash.core.eh.exception.base.AbstractApplicationException;

/**
 * Base class for operation-related exceptions in the application.
 */
public abstract class OperationException extends AbstractApplicationException {

    protected OperationException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected OperationException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected OperationException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
