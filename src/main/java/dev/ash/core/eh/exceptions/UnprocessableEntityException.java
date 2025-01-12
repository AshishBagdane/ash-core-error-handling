package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when request cannot be processed despite correct syntax. Used for semantic errors in request processing.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public final class UnprocessableEntityException extends AbstractException {

  public UnprocessableEntityException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public UnprocessableEntityException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNPROCESSABLE_ENTITY;
  }

  /**
   * Factory method for unprocessable entity errors
   */
  public static UnprocessableEntityException createError(String reason) {
    return new UnprocessableEntityException(
        new ErrorMessage(ErrorCode.HTTP_UNPROCESSABLE_ENTITY)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_UNPROCESSABLE_ENTITY,
                "Request cannot be processed: %s".formatted(reason)
            ))
    );
  }
}