package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when API gateway timeout occurs. Used for timeout in API calls.
 */
@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public final class GatewayTimeoutException extends AbstractException {

  public GatewayTimeoutException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public GatewayTimeoutException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.GATEWAY_TIMEOUT;
  }

  /**
   * Factory method for gateway timeouts
   */
  public static GatewayTimeoutException createError(String service, long timeout) {
    return new GatewayTimeoutException(
        new ErrorMessage(ErrorCode.HTTP_GATEWAY_TIMEOUT)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_GATEWAY_TIMEOUT,
                "Request to %s timed out after %d ms".formatted(service, timeout)
            ))
    );
  }
}