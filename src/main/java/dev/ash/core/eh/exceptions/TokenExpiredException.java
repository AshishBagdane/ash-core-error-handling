package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authentication token has expired. Specific to JWT/token-based authentication.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public final class TokenExpiredException extends AbstractException {

  public TokenExpiredException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public TokenExpiredException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }

  /**
   * Factory method for token expiration
   */
  public static TokenExpiredException createError(String tokenType) {
    return new TokenExpiredException(
        new ErrorMessage(ErrorCode.SECURITY_TOKEN_EXPIRED)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.SECURITY_TOKEN_EXPIRED,
                "%s token has expired. Please authenticate again.".formatted(tokenType)
            ))
    );
  }
}