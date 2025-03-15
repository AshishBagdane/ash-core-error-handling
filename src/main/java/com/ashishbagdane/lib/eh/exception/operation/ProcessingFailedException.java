package com.ashishbagdane.lib.eh.exception.operation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;

/**
 * Exception thrown when a processing operation fails.
 */
public class ProcessingFailedException extends OperationException {

    public ProcessingFailedException(String operation, String reason) {
        super(
            ErrorCode.SYSTEM_ERROR,
            String.format("Failed to process operation '%s': %s", operation, reason),
            createErrorContext()
                .attribute("operation", operation)
                .attribute("reason", reason)
                .build()
        );
    }

    public ProcessingFailedException(String operation, Throwable cause) {
        super(
            ErrorCode.SYSTEM_ERROR,
            cause,
            createErrorContext()
                .attribute("operation", operation)
                .attribute("errorType", cause.getClass().getSimpleName())
                .build()
        );
    }
}
