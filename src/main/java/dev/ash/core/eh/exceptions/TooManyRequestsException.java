package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when rate limit is exceeded. Used for API rate limiting scenarios.
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public final class TooManyRequestsException extends AbstractException {

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.TOO_MANY_REQUESTS;
  }

  public TooManyRequestsException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public TooManyRequestsException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  /**
   * Factory method for rate limit exceeded
   */
  public static TooManyRequestsException createError(long retryAfterSeconds) {
    return new TooManyRequestsException(
        new ErrorMessage(ErrorCode.HTTP_RATE_LIMIT_EXCEEDED)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_RATE_LIMIT_EXCEEDED,
                "Rate limit exceeded. Please try again after %d seconds".formatted(retryAfterSeconds)
            ))
    );
  }
}