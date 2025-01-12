package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when resource state transition is invalid. Used for state machine violations in business processes.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class StateConflictException extends BusinessException {

  public StateConflictException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public StateConflictException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.CONFLICT;
  }

  /**
   * Factory method for invalid state transitions
   *
   * @param resource     The resource type (e.g., "Order", "Payment")
   * @param currentState Current state of the resource
   * @param targetState  Attempted target state
   * @return StateConflictException with formatted error message
   */
  public static StateConflictException createError(String resource, String currentState, String targetState) {
    return new StateConflictException(
        new ErrorMessage(ErrorCode.BUSINESS_INVALID_TRANSITION)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.BUSINESS_INVALID_TRANSITION,
                "Cannot transition %s from '%s' to '%s'".formatted(resource, currentState, targetState)
            ))
    );
  }

  /**
   * Factory method for invalid state operations
   *
   * @param resource     The resource type
   * @param currentState Current state of the resource
   * @param operation    Attempted operation
   * @return StateConflictException with formatted error message
   */
  public static StateConflictException invalidOperation(String resource, String currentState, String operation) {
    return new StateConflictException(
        new ErrorMessage(ErrorCode.BUSINESS_INVALID_OPERATION)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.BUSINESS_INVALID_OPERATION,
                "Cannot perform '%s' on %s in state '%s'".formatted(operation, resource, currentState)
            ))
    );
  }
}