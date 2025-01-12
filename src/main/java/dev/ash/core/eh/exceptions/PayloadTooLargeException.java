package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when request payload is too large. Used for request size limit violations.
 */
@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public final class PayloadTooLargeException extends AbstractException {

  public PayloadTooLargeException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public PayloadTooLargeException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.PAYLOAD_TOO_LARGE;
  }

  /**
   * Factory method for payload size errors
   */
  public static PayloadTooLargeException createError(long size, long maxSize) {
    return new PayloadTooLargeException(
        new ErrorMessage(ErrorCode.HTTP_PAYLOAD_TOO_LARGE)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_PAYLOAD_TOO_LARGE,
                "Request size of %d bytes exceeds maximum allowed size of %d bytes".formatted(size, maxSize)
            ))
    );
  }
}