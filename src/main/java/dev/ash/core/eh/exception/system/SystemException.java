package dev.ash.core.eh.exception.system;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.core.ErrorContext;
import dev.ash.core.eh.exception.base.AbstractApplicationException;

/**
 * Base class for system-level exceptions in the application.
 */
public abstract class SystemException extends AbstractApplicationException {

    protected SystemException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected SystemException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected SystemException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
