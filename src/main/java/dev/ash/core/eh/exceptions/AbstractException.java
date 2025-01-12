package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * Base abstract class for all custom exceptions in the application. Provides common functionality for error handling and logging. This class is sealed and only permits specific exception types.
 */
@Slf4j
@Getter
// @formatter:off
public abstract sealed class AbstractException extends RuntimeException
    permits BadRequestException, NotFoundException, PreconditionFailedException,
    StorageException, InternalServerErrorException, BusinessException,
    UnauthorizedException, ForbiddenException, TokenExpiredException,
    ConflictException, DataIntegrityException,
    ServiceUnavailableException, GatewayTimeoutException, TooManyRequestsException,
    UnsupportedMediaTypeException, PayloadTooLargeException, UnprocessableEntityException {

  // @formatter:on
  private ErrorMessage errorMessage;

  /**
   * Constructs an AbstractException with an ErrorCode.
   *
   * @param errorCode The error code enum
   */
  protected AbstractException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorMessage = new ErrorMessage(errorCode);
  }

  /**
   * Constructs an AbstractException with an ErrorMessage.
   *
   * @param errorMessage The error details
   */
  protected AbstractException(ErrorMessage errorMessage) {
    super(errorMessage.getMessage());
    this.errorMessage = errorMessage;
  }

  /**
   * Constructs an AbstractException with an ErrorCode and a cause.
   *
   * @param errorCode The error code enum
   * @param cause     The cause of the exception
   */
  protected AbstractException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorMessage = new ErrorMessage(errorCode);
  }

  /**
   * Adds a developer message to the error details.
   *
   * @param developerMessage The developer-specific message
   * @return this exception instance for chaining
   */
  public AbstractException addDeveloperMessage(DeveloperErrorMessage developerMessage) {
    this.errorMessage = this.errorMessage.addDeveloperMessage(developerMessage);
    return this;
  }

  /**
   * Adds a developer message using an error code.
   *
   * @param errorCode The error code to add as a developer message
   * @return this exception instance for chaining
   */
  public AbstractException addDeveloperMessage(ErrorCode errorCode) {
    this.errorMessage = this.errorMessage.addDeveloperMessage(errorCode);
    return this;
  }

  /**
   * Logs the error message with appropriate level based on HTTP status. Uses structured logging format for better readability.
   */
  public void logErrorMessage() {
    String errorLog = """
        Exception Details:
        Type: %s
        Status: %s
        Code: %d
        Message: %s
        Developer Messages: %s
        """.formatted(
        this.getClass().getSimpleName(),
        this.getHttpStatus(),
        this.errorMessage.getCode(),
        this.errorMessage.getMessage(),
        this.errorMessage.hasErrors() ? this.errorMessage.developerMessages() : "None"
    );

    if (this.getHttpStatus().is5xxServerError()) {
      log.error(errorLog, this);
    } else {
      log.warn(errorLog);
    }

    if (this.getCause() != null) {
      log.error("Caused by: ", this.getCause());
    }
  }

  /**
   * Gets the HTTP status associated with this exception. Should be implemented by each concrete exception class.
   *
   * @return the HTTP status code
   */
  public abstract HttpStatus getHttpStatus();
}