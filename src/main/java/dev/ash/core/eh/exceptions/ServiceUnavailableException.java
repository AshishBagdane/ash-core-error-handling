package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a dependent service is unavailable. Used for service dependency failures.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public final class ServiceUnavailableException extends AbstractException {

  public ServiceUnavailableException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public ServiceUnavailableException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.SERVICE_UNAVAILABLE;
  }

  /**
   * Factory method for service unavailability
   */
  public static ServiceUnavailableException createError(String serviceName) {
    return new ServiceUnavailableException(
        new ErrorMessage(ErrorCode.INTEGRATION_SERVICE_UNAVAILABLE_ERROR)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.INTEGRATION_SERVICE_UNAVAILABLE_ERROR,
                "The %s service is currently unavailable. Please try again later.".formatted(serviceName)
            ))
    );
  }
}