package com.ashishbagdane.lib.eh.exception.operation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.base.eh.core.ErrorContext;
import com.ashishbagdane.lib.base.eh.exception.base.AbstractApplicationException;

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
