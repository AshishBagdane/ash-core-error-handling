package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authenticated user doesn't have required permissions. Used for authorization failures.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public final class ForbiddenException extends AbstractException {

  public ForbiddenException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public ForbiddenException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.FORBIDDEN;
  }

  /**
   * Factory method for creating a standard forbidden error
   */
  public static ForbiddenException standardError(String resource, String action) {
    return new ForbiddenException(
        new ErrorMessage(ErrorCode.HTTP_FORBIDDEN)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_FORBIDDEN,
                "You don't have permission to %s this %s".formatted(action, resource)
            ))
    );
  }
}