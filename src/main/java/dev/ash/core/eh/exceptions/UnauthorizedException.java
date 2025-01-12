package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public final class UnauthorizedException extends AbstractException {

  public UnauthorizedException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public UnauthorizedException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }

  /**
   * Factory method for creating a standard unauthorized error
   */
  public static UnauthorizedException standardError(String message) {
    return new UnauthorizedException(
        new ErrorMessage(ErrorCode.HTTP_UNAUTHORIZED)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_UNAUTHORIZED,
                message
            ))
    );
  }
}