package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling storage-related errors. Used when operations involving data storage fail.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class StorageException extends AbstractException {

  public StorageException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public StorageException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}