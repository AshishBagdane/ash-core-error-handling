package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource conflict occurs. Used for duplicate resources or other conflict scenarios.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class ConflictException extends AbstractException {

  public ConflictException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public ConflictException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.CONFLICT;
  }

  /**
   * Factory method for duplicate resource conflicts
   */
  public static ConflictException duplicateResource(String resourceType, String identifier) {
    return new ConflictException(
        new ErrorMessage(ErrorCode.HTTP_RESOURCE_CONFLICT)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_RESOURCE_CONFLICT,
                "%s with identifier %s already exists".formatted(resourceType, identifier)
            ))
    );
  }
}