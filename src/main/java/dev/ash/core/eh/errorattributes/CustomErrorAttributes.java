package dev.ash.core.eh.errorattributes;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import dev.ash.core.eh.exceptions.AbstractException;
import dev.ash.core.eh.exceptions.BadRequestException;
import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

/**
 * Customizes and extends Spring Boot's error handling capabilities by providing enhanced error attributes. This component processes different types of exceptions and standardizes error responses
 * across the application. It handles both custom application exceptions ({@link AbstractException}) and validation errors ({@link MethodArgumentNotValidException}).
 *
 * <p>Key features:
 * <ul>
 *     <li>Standardizes error response format</li>
 *     <li>Adds timestamp to all error responses</li>
 *     <li>Removes stack traces from responses for security</li>
 *     <li>Provides detailed validation error messages</li>
 *     <li>Integrates with MDC for enhanced logging</li>
 * </ul>
 *
 * @see DefaultErrorAttributes
 * @see ErrorAttributeOptions
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  private static final Logger log = LoggerFactory.getLogger(CustomErrorAttributes.class);

  /**
   * Customizes error attributes for the error response. This method overrides the default Spring Boot error attributes to provide a standardized error response format across the application.
   *
   * @param webRequest the current web request
   * @param options    options for error attribute inclusion
   * @return a map of error attributes to be used in the error response
   */
  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
    customizeBasicAttributes(errorAttributes);

    Throwable error = getError(webRequest);
    if (error != null) {
      handleError(error, errorAttributes);
    }

    return errorAttributes;
  }

  /**
   * Customizes the basic attributes of the error response. Removes potentially sensitive information and adds timestamp.
   *
   * @param attributes the map of error attributes to customize
   */
  private void customizeBasicAttributes(Map<String, Object> attributes) {
    attributes.remove("trace");  // Remove stack trace for security
    attributes.put("timestamp", LocalDateTime.now());
  }

  /**
   * Handles different types of errors using instanceof matching. Delegates to specific handlers based on the error type.
   *
   * @param error      the thrown error to handle
   * @param attributes the map of error attributes to update
   */
  private void handleError(Throwable error, Map<String, Object> attributes) {
    if (error instanceof AbstractException exception) {
      handleAbstractException(exception, attributes);
    } else if (error instanceof MethodArgumentNotValidException validationError) {
      handleValidationException(validationError, attributes);
    } else {
      log.error(error.getMessage());
    }
  }

  /**
   * Processes application-specific exceptions extending AbstractException. Updates error attributes with custom error codes and messages.
   *
   * @param exception  the application exception to handle
   * @param attributes the map of error attributes to update
   */
  private void handleAbstractException(AbstractException exception, Map<String, Object> attributes) {
    ErrorMessage errorMessage = exception.getErrorMessage();
    ErrorCode errorCode = errorMessage.errorCode();

    updateErrorAttributes(attributes,
        errorCode.getCode(),
        errorCode.getMessage(),
        errorMessage.developerMessages());

    logError(attributes, errorCode.getCode(), errorCode.getMessage(),
        exception.getClass().getSimpleName() + ": " + errorMessage);
  }

  /**
   * Handles validation exceptions from request body or form data validation. Converts validation errors into a standardized format with field-specific error messages.
   *
   * @param exception  the validation exception to handle
   * @param attributes the map of error attributes to update
   */
  private void handleValidationException(MethodArgumentNotValidException exception, Map<String, Object> attributes) {
    BadRequestException badRequest = createBadRequestFromValidation(exception.getBindingResult());
    ErrorMessage errorMessage = badRequest.getErrorMessage();

    updateErrorAttributes(attributes,
        errorMessage.errorCode().getCode(),
        errorMessage.errorCode().getMessage(),
        errorMessage.developerMessages());

    logError(attributes,
        errorMessage.errorCode().getCode(),
        errorMessage.errorCode().getMessage(),
        "Validation Error");
  }

  /**
   * Creates a BadRequestException from validation binding results. Converts field errors into developer error messages.
   *
   * @param bindingResult the validation binding result containing field errors
   * @return a BadRequestException containing all validation errors
   */
  private BadRequestException createBadRequestFromValidation(BindingResult bindingResult) {
    BadRequestException exception = new BadRequestException(new ErrorMessage(ErrorCode.VALIDATION_ERROR));

    bindingResult.getFieldErrors().forEach(fieldError -> {
      DeveloperErrorMessage errorMessage = new DeveloperErrorMessage(
          ErrorCode.VALIDATION_INVALID_PARAMETER,
          fieldError.getField() + " " + fieldError.getDefaultMessage()
      );
      exception.getErrorMessage().addDeveloperMessage(errorMessage);
    });

    return exception;
  }

  /**
   * Updates the error attributes map with standardized error information. Sets code, message, and developer messages in the response.
   *
   * @param attributes        the map of error attributes to update
   * @param code              the error code
   * @param message           the error message
   * @param developerMessages detailed messages for developers
   */
  private void updateErrorAttributes(Map<String, Object> attributes, int code, String message, Object developerMessages) {
    attributes.put("code", code);
    attributes.put("message", message);
    attributes.put("developerMessages", developerMessages);
  }

  /**
   * Logs error information and updates the MDC context. Enhances logging with error details and HTTP status information.
   *
   * @param attributes   the current error attributes
   * @param code         the error code to log
   * @param message      the error message to log
   * @param errorDetails additional error details for logging
   */
  private void logError(Map<String, Object> attributes, int code, String message, String errorDetails) {
    String httpStatus = attributes.get("status") + " - " + attributes.get("error");

    MDC.put("b.code", String.valueOf(code));
    MDC.put("b.message", message);
    MDC.put("b.httpStatus", httpStatus);

    log.error(errorDetails);
  }
}