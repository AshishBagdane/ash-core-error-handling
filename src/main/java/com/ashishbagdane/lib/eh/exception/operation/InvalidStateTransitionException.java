package com.ashishbagdane.lib.eh.exception.operation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.base.eh.core.ErrorContext;

/**
 * Exception thrown when an invalid state transition is attempted.
 */
public class InvalidStateTransitionException extends OperationException {

    public InvalidStateTransitionException(String entityType, String entityId,
                                           String currentState, String targetState) {
        super(
            ErrorCode.BUSINESS_INVALID_TRANSITION,
            String.format("Cannot transition %s (ID: %s) from '%s' to '%s'",
                          entityType, entityId, currentState, targetState),
            ErrorContext.builder()
                .attribute("entityType", entityType)
                .attribute("entityId", entityId)
                .attribute("currentState", currentState)
                .attribute("targetState", targetState)
                .build()
        );
    }

    public InvalidStateTransitionException(String entityType, String entityId,
                                           String currentState, String targetState,
                                           String reason) {
        super(
            ErrorCode.BUSINESS_INVALID_TRANSITION,
            String.format("Cannot transition %s (ID: %s) from '%s' to '%s': %s",
                          entityType, entityId, currentState, targetState, reason),
            createErrorContext()
                .attribute("entityType", entityType)
                .attribute("entityId", entityId)
                .attribute("currentState", currentState)
                .attribute("targetState", targetState)
                .attribute("reason", reason)
                .build()
        );
    }
}
