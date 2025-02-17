package dev.ash.core.eh.exception.base;

import dev.ash.core.eh.core.ErrorCode;
import dev.ash.core.eh.core.ErrorContext;
import dev.ash.core.eh.core.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Base exception class for all application exceptions. Provides consistent error handling and message formatting.
 */
@Getter
public abstract class AbstractApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    private final ErrorMessage errorMessage;

    private final ErrorContext errorContext;

    /**
     * Constructs a new exception with the specified error code and context.
     *
     * @param errorCode    The specific error code for this exception
     * @param errorContext The context containing additional error details
     */
    protected AbstractApplicationException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
        this.errorContext = errorContext;
        this.errorMessage = ErrorMessage.from(errorCode, errorContext);
    }

    /**
     * Constructs a new exception with the specified error code, message and context.
     *
     * @param errorCode    The specific error code for this exception
     * @param message      Custom error message
     * @param errorContext The context containing additional error details
     */
    protected AbstractApplicationException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(message);
        this.errorCode = errorCode;
        this.errorContext = errorContext;
        this.errorMessage = ErrorMessage.builder()
            .timestamp(LocalDateTime.now())
            .status(errorCode.getHttpStatus().value())
            .code(errorCode.getCode())
            .error(errorCode.getCategory().getDescription())
            .message(message)
            .path(errorContext.getPath())
            .traceId(errorContext.getTraceId())
            .details(errorContext.getAttributes())
            .build();
    }

    /**
     * Constructs a new exception with the specified error code, cause and context.
     *
     * @param errorCode    The specific error code for this exception
     * @param cause        The cause of this exception
     * @param errorContext The context containing additional error details
     */
    protected AbstractApplicationException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode.getDefaultMessage(), cause);
        this.errorCode = errorCode;
        this.errorContext = errorContext;
        this.errorMessage = ErrorMessage.from(errorCode, errorContext);
    }

    /**
     * Gets the HTTP status associated with this exception.
     *
     * @return The HTTP status code
     */
    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    /**
     * Creates a builder for the exception context.
     *
     * @return A new ErrorContext.Builder instance
     */
    protected static ErrorContext.Builder createErrorContext() {
        return ErrorContext.builder();
    }
}
