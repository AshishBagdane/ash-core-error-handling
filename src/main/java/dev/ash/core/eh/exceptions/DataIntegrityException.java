package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when database constraints are violated. Used for database integrity issues.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class DataIntegrityException extends AbstractException {

  public DataIntegrityException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public DataIntegrityException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.CONFLICT;
  }

  /**
   * Factory method for foreign key violations
   */
  public static DataIntegrityException foreignKeyViolation(String entity, String relatedEntity) {
    return new DataIntegrityException(
        new ErrorMessage(ErrorCode.DATA_INTEGRITY_ERROR)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.DATA_INTEGRITY_ERROR,
                "Cannot process %s due to dependencies in %s".formatted(entity, relatedEntity)
            ))
    );
  }
}