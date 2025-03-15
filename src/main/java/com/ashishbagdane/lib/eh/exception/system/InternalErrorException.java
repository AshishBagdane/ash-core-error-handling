package com.ashishbagdane.lib.eh.exception.system;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;

/**
 * Exception thrown when an unexpected internal error occurs.
 */
public class InternalErrorException extends SystemException {

    public InternalErrorException(String operation, Throwable cause) {
        super(
            ErrorCode.HTTP_INTERNAL_SERVER_ERROR,
            cause,
            createErrorContext()
                .attribute("operation", operation)
                .attribute("errorType", cause.getClass().getSimpleName())
                .attribute("errorMessage", cause.getMessage())
                .build()
        );
    }

    public InternalErrorException(String operation, String reason) {
        super(
            ErrorCode.HTTP_INTERNAL_SERVER_ERROR,
            String.format("Internal error during '%s': %s", operation, reason),
            createErrorContext()
                .attribute("operation", operation)
                .attribute("reason", reason)
                .build()
        );
    }
}
