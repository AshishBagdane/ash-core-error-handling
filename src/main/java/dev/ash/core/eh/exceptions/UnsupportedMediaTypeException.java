package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when media type is not supported. Used for unsupported content types.
 */
@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public final class UnsupportedMediaTypeException extends AbstractException {

  public UnsupportedMediaTypeException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public UnsupportedMediaTypeException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
  }

  /**
   * Factory method for unsupported media types
   */
  public static UnsupportedMediaTypeException createError(String mediaType, String... supportedTypes) {
    return new UnsupportedMediaTypeException(
        new ErrorMessage(ErrorCode.HTTP_UNSUPPORTED_MEDIA_TYPE)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_UNSUPPORTED_MEDIA_TYPE,
                "Media type '%s' is not supported. Supported types: %s".formatted(
                    mediaType, String.join(", ", supportedTypes))
            ))
    );
  }
}