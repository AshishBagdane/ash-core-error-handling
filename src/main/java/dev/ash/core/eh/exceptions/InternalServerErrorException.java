package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling Internal Server Error (500) scenarios. Used when an unexpected error occurs during request processing.
 *
 * @since 1.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class InternalServerErrorException extends AbstractException {

  public InternalServerErrorException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public InternalServerErrorException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}