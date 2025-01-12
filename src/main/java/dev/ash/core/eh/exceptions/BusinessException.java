package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base exception for business rule violations. Used as a parent class for specific business exceptions.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public sealed class BusinessException extends AbstractException
    permits StateConflictException, ResourceLockedException {

  public BusinessException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public BusinessException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  /**
   * Factory method for general business rule violations
   */
  public static BusinessException createError(String rule, String details) {
    return new BusinessException(
        new ErrorMessage(ErrorCode.BUSINESS_RULE_VIOLATION)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.BUSINESS_RULE_VIOLATION,
                "Rule '%s' violated: %s".formatted(rule, details)
            ))
    );
  }
}