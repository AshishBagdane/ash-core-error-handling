package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for handling Bad Request (400) scenarios. Used when the request is malformed or contains invalid parameters.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class BadRequestException extends AbstractException {

  public BadRequestException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public BadRequestException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage.errorCode(), cause);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  /**
   * Factory method for invalid parameter errors
   */
  public static BadRequestException invalidParameter(String paramName, String details) {
    return new BadRequestException(
        new ErrorMessage(ErrorCode.VALIDATION_INVALID_PARAMETER)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.VALIDATION_INVALID_PARAMETER,
                "Parameter '%s' is invalid: %s".formatted(paramName, details)
            ))
    );
  }

  /**
   * Factory method for malformed request errors
   */
  public static BadRequestException malformedRequest(String details) {
    return new BadRequestException(
        new ErrorMessage(ErrorCode.VALIDATION_MALFORMED_REQUEST)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.VALIDATION_MALFORMED_REQUEST,
                "Malformed request: %s".formatted(details)
            ))
    );
  }

  /**
   * Factory method for missing required field errors
   */
  public static BadRequestException missingRequiredField(String fieldName) {
    return new BadRequestException(
        new ErrorMessage(ErrorCode.VALIDATION_MISSING_FIELD)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.VALIDATION_MISSING_FIELD,
                "Required field '%s' is missing".formatted(fieldName)
            ))
    );
  }

  /**
   * Factory method for validation errors with multiple field errors
   */
  public static BadRequestException validationError(Map<String, String> fieldErrors) {
    ErrorMessage errorMessage = new ErrorMessage(ErrorCode.VALIDATION_ERROR);
    fieldErrors.forEach((field, error) ->
        errorMessage.addDeveloperMessage(DeveloperErrorMessage.fieldError(field, error))
    );
    return new BadRequestException(errorMessage);
  }
}