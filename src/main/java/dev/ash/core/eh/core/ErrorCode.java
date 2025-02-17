package dev.ash.core.eh.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Comprehensive error codes for your Business application/Library/Module.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // HTTP Standard Errors (4xx, 5xx)
    HTTP_BAD_REQUEST(ErrorCategory.HTTP_STANDARD, 400, HttpStatus.BAD_REQUEST,
                     "The request cannot be processed due to client error"),
    HTTP_UNAUTHORIZED(ErrorCategory.HTTP_STANDARD, 401, HttpStatus.UNAUTHORIZED,
                      "Authentication is required to access this resource"),
    HTTP_FORBIDDEN(ErrorCategory.HTTP_STANDARD, 403, HttpStatus.FORBIDDEN,
                   "You don't have permission to access this resource"),
    HTTP_NOT_FOUND(ErrorCategory.HTTP_STANDARD, 404, HttpStatus.NOT_FOUND, "The requested resource was not found"),
    HTTP_METHOD_NOT_ALLOWED(ErrorCategory.HTTP_STANDARD, 405, HttpStatus.METHOD_NOT_ALLOWED,
                            "The requested method is not supported"),
    HTTP_NOT_ACCEPTABLE(ErrorCategory.HTTP_STANDARD, 406, HttpStatus.NOT_ACCEPTABLE,
                        "The requested format is not available"),
    HTTP_RESOURCE_CONFLICT(ErrorCategory.HTTP_STANDARD, 409, HttpStatus.CONFLICT,
                           "The resource already exists in the system"),
    HTTP_PRECONDITION_FAILED(ErrorCategory.HTTP_STANDARD, 412, HttpStatus.PRECONDITION_FAILED,
                             "Precondition for the request failed"),
    HTTP_PAYLOAD_TOO_LARGE(ErrorCategory.HTTP_STANDARD, 413, HttpStatus.PAYLOAD_TOO_LARGE,
                           "The request payload exceeds the size limit"),
    HTTP_UNSUPPORTED_MEDIA_TYPE(ErrorCategory.HTTP_STANDARD, 415, HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                                "The requested media type is not supported"),
    HTTP_UNPROCESSABLE_ENTITY(ErrorCategory.HTTP_STANDARD, 422, HttpStatus.UNPROCESSABLE_ENTITY,
                              "The request was well-formed but cannot be processed"),
    HTTP_RESOURCE_LOCKED(ErrorCategory.HTTP_STANDARD, 423, HttpStatus.LOCKED,
                         "The requested resource is currently locked"),
    HTTP_RATE_LIMIT_EXCEEDED(ErrorCategory.HTTP_STANDARD, 429, HttpStatus.TOO_MANY_REQUESTS,
                             "Request limit exceeded. Please try again later"),
    HTTP_INTERNAL_SERVER_ERROR(ErrorCategory.HTTP_STANDARD, 500, HttpStatus.INTERNAL_SERVER_ERROR,
                               "An unexpected error occurred. Please try again later"),
    HTTP_NOT_IMPLEMENTED(ErrorCategory.HTTP_STANDARD, 501, HttpStatus.NOT_IMPLEMENTED,
                         "This feature is not yet implemented"),
    HTTP_BAD_GATEWAY(ErrorCategory.HTTP_STANDARD, 502, HttpStatus.BAD_GATEWAY,
                     "The server received an invalid response"),
    HTTP_SERVICE_UNAVAILABLE(ErrorCategory.HTTP_STANDARD, 503, HttpStatus.SERVICE_UNAVAILABLE,
                             "The service is temporarily unavailable"),
    HTTP_GATEWAY_TIMEOUT(ErrorCategory.HTTP_STANDARD, 504, HttpStatus.GATEWAY_TIMEOUT,
                         "The server timed out waiting for a response"),

    // Validation Errors (1xxx)
    VALIDATION_ERROR(ErrorCategory.VALIDATION, 1000, HttpStatus.BAD_REQUEST, "One or more validation errors occurred"),
    VALIDATION_REQUIRED_FIELD(ErrorCategory.VALIDATION, 1001, HttpStatus.BAD_REQUEST, "This field is required"),
    VALIDATION_MISSING_FIELD(ErrorCategory.VALIDATION, 1002, HttpStatus.BAD_REQUEST,
                             "A required field is missing from the request"),
    VALIDATION_INVALID_EMAIL(ErrorCategory.VALIDATION, 1003, HttpStatus.BAD_REQUEST,
                             "Please enter a valid email address"),
    VALIDATION_INVALID_PHONE(ErrorCategory.VALIDATION, 1004, HttpStatus.BAD_REQUEST,
                             "Please enter a valid phone number"),
    VALIDATION_INVALID_DATE(ErrorCategory.VALIDATION, 1005, HttpStatus.BAD_REQUEST, "Please enter a valid date"),
    VALIDATION_INVALID_FORMAT(ErrorCategory.VALIDATION, 1006, HttpStatus.BAD_REQUEST,
                              "The provided format is incorrect"),
    VALIDATION_INVALID_PARAMETER(ErrorCategory.VALIDATION, 1007, HttpStatus.BAD_REQUEST,
                                 "One or more parameters are invalid"),
    VALIDATION_MALFORMED_REQUEST(ErrorCategory.VALIDATION, 1008, HttpStatus.BAD_REQUEST,
                                 "The request format is invalid or malformed"),

    // Business Errors (2xxx)
    BUSINESS_DUPLICATE_ENTRY(ErrorCategory.BUSINESS, 2000, HttpStatus.CONFLICT, "This entry already exists"),
    BUSINESS_INVALID_STATE(ErrorCategory.BUSINESS, 2001, HttpStatus.CONFLICT, "Invalid state for this operation"),
    BUSINESS_INVALID_OPERATION(ErrorCategory.BUSINESS, 2002, HttpStatus.UNPROCESSABLE_ENTITY,
                               "The requested operation is invalid in the current state"),
    BUSINESS_INVALID_TRANSITION(ErrorCategory.BUSINESS, 2003, HttpStatus.CONFLICT,
                                "Cannot transition to the requested state"),
    BUSINESS_EXPIRED_RESOURCE(ErrorCategory.BUSINESS, 2004, HttpStatus.GONE, "This resource has expired"),
    BUSINESS_RULE_VIOLATION(ErrorCategory.BUSINESS, 2005, HttpStatus.UNPROCESSABLE_ENTITY,
                            "This operation violates business rules"),

    // Security Errors (3xxx)
    SECURITY_TOKEN_EXPIRED(ErrorCategory.SECURITY, 3000, HttpStatus.UNAUTHORIZED,
                           "Your session has expired. Please log in again"),
    SECURITY_INVALID_CREDENTIALS(ErrorCategory.SECURITY, 3001, HttpStatus.UNAUTHORIZED, "Invalid username or password"),
    SECURITY_ACCOUNT_LOCKED(ErrorCategory.SECURITY, 3002, HttpStatus.FORBIDDEN,
                            "Account has been locked. Please contact support"),
    SECURITY_INVALID_TOKEN(ErrorCategory.SECURITY, 3003, HttpStatus.UNAUTHORIZED,
                           "Invalid or malformed authentication token"),
    SECURITY_PASSWORD_EXPIRED(ErrorCategory.SECURITY, 3004, HttpStatus.UNAUTHORIZED,
                              "Password has expired. Please reset your password"),
    SECURITY_INSUFFICIENT_PERMISSIONS(ErrorCategory.SECURITY, 3005, HttpStatus.FORBIDDEN,
                                      "You don't have sufficient permissions"),
    SECURITY_INVALID_2FA_CODE(ErrorCategory.SECURITY, 3006, HttpStatus.UNAUTHORIZED,
                              "Invalid two-factor authentication code"),

    // Data Errors (4xxx)
    DATA_INTEGRITY_ERROR(ErrorCategory.DATA, 4000, HttpStatus.CONFLICT, "Data integrity constraint has been violated"),
    DATA_INTEGRITY_VIOLATION(ErrorCategory.DATA, 4001, HttpStatus.CONFLICT,
                             "Unable to process due to data integrity constraints"),
    DATA_STALE(ErrorCategory.DATA, 4002, HttpStatus.PRECONDITION_FAILED,
               "The data has been modified. Please refresh and try again"),
    DATA_NOT_FOUND(ErrorCategory.DATA, 4003, HttpStatus.NOT_FOUND, "Required data was not found"),
    DATA_ALREADY_EXISTS(ErrorCategory.DATA, 4004, HttpStatus.CONFLICT, "This data already exists"),
    DATA_CORRUPTED(ErrorCategory.DATA, 4005, HttpStatus.UNPROCESSABLE_ENTITY, "The data appears to be corrupted"),
    DATA_INVALID_REFERENCE(ErrorCategory.DATA, 4006, HttpStatus.BAD_REQUEST, "Invalid reference to related data"),

    // Integration Errors (5xxx)
    INTEGRATION_EXTERNAL_SERVICE_ERROR(ErrorCategory.INTEGRATION, 5000, HttpStatus.BAD_GATEWAY,
                                       "External service error occurred"),
    INTEGRATION_INTEGRATION_TIMEOUT(ErrorCategory.INTEGRATION, 5001, HttpStatus.GATEWAY_TIMEOUT,
                                    "Integration request timed out"),
    INTEGRATION_INVALID_RESPONSE(ErrorCategory.INTEGRATION, 5002, HttpStatus.BAD_GATEWAY,
                                 "Received invalid response from external service"),
    INTEGRATION_SERVICE_UNAVAILABLE_ERROR(ErrorCategory.INTEGRATION, 5003, HttpStatus.SERVICE_UNAVAILABLE,
                                          "External service is currently unavailable"),
    INTEGRATION_API_LIMIT_EXCEEDED(ErrorCategory.INTEGRATION, 5004, HttpStatus.TOO_MANY_REQUESTS,
                                   "External API rate limit exceeded"),

    // System Errors (9xxx)
    SYSTEM_ERROR(ErrorCategory.SYSTEM, 9000, HttpStatus.INTERNAL_SERVER_ERROR, "A system error has occurred"),
    SYSTEM_CONFIGURATION_ERROR(ErrorCategory.SYSTEM, 9001, HttpStatus.INTERNAL_SERVER_ERROR,
                               "System configuration error"),
    SYSTEM_RESOURCE_EXHAUSTED(ErrorCategory.SYSTEM, 9002, HttpStatus.SERVICE_UNAVAILABLE,
                              "System resources are exhausted"),
    SYSTEM_MAINTENANCE_MODE(ErrorCategory.SYSTEM, 9003, HttpStatus.SERVICE_UNAVAILABLE, "System is under maintenance"),
    SYSTEM_CRITICAL_ERROR(ErrorCategory.SYSTEM, 9004, HttpStatus.INTERNAL_SERVER_ERROR,
                          "A critical system error has occurred");

    private final ErrorCategory category;

    private final Integer code;

    private final HttpStatus httpStatus;

    private final String defaultMessage;


    /**
     * Gets the category of the error code based on its numerical value.
     *
     * @return The category of the error code
     */
    public ErrorCategory getCategory() {
        if (name().startsWith("HTTP_")) {
            return ErrorCategory.HTTP_STANDARD;
        }
        return switch (code / 1000) {
            case 1 -> ErrorCategory.VALIDATION;
            case 2 -> ErrorCategory.BUSINESS;
            case 3 -> ErrorCategory.SECURITY;
            case 4 -> ErrorCategory.DATA;
            case 5 -> ErrorCategory.INTEGRATION;
            case 9 -> ErrorCategory.SYSTEM;
            default -> throw new IllegalStateException("Unknown category for code: " + code);
        };
    }

    /**
     * Checks if the error code represents a client error (4xx).
     *
     * @return true if it's a client error, false otherwise
     */
    public boolean isClientError() {
        int status = httpStatus.value();
        return status >= 400 && status < 500;
    }

    /**
     * Checks if the error code represents a server error (5xx).
     *
     * @return true if it's a server error, false otherwise
     */
    public boolean isServerError() {
        int status = httpStatus.value();
        return status >= 500 && status < 600;
    }
}
