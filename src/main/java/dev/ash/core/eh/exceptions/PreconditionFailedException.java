package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling Precondition Failed (412) scenarios. Used when server-side preconditions for the request fail.
 */
@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public final class PreconditionFailedException extends AbstractException {

  private static final long serialVersionUID = 1L;

  public PreconditionFailedException(ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public PreconditionFailedException(ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.PRECONDITION_FAILED;
  }
}