package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling Not Found (404) scenarios. Used when requested resource is not found in the system.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class NotFoundException extends AbstractException {

  public NotFoundException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public NotFoundException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}