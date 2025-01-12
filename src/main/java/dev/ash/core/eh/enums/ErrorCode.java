package dev.ash.core.eh.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Standardized error codes for the application. Categorized into HTTP errors, validation errors, and business errors.
 * <p>
 * Each error code consists of: - HTTP (4xx, 5xx): Standard HTTP error codes - Validation (1xxx): Input validation related errors - Business (2xxx): Business rule and logic related errors - Security
 * (3xxx): Security and access related errors - Data (4xxx): Data handling and persistence errors - Integration (5xxx): External service and integration errors - System (9xxx): System and
 * infrastructure errors
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

  // HTTP Standard Errors (4xx, 5xx)
  HTTP_BAD_REQUEST(400, "The request cannot be processed due to client error"),
  HTTP_UNAUTHORIZED(401, "Authentication is required to access this resource"),
  HTTP_FORBIDDEN(403, "You don't have permission to access this resource"),
  HTTP_NOT_FOUND(404, "The requested resource was not found"),
  HTTP_METHOD_NOT_ALLOWED(405, "The requested method is not supported"),
  HTTP_NOT_ACCEPTABLE(406, "The requested format is not available"),
  HTTP_RESOURCE_CONFLICT(409, "The resource already exists in the system"),
  HTTP_PRECONDITION_FAILED(412, "Precondition for the request failed"),
  HTTP_PAYLOAD_TOO_LARGE(413, "The request payload exceeds the size limit"),
  HTTP_UNSUPPORTED_MEDIA_TYPE(415, "The requested media type is not supported"),
  HTTP_UNPROCESSABLE_ENTITY(422, "The request was well-formed but cannot be processed"),
  HTTP_RESOURCE_LOCKED(423, "The requested resource is currently locked"),
  HTTP_RATE_LIMIT_EXCEEDED(429, "Request limit exceeded. Please try again later"),
  HTTP_INTERNAL_SERVER_ERROR(500, "An unexpected error occurred. Please try again later"),
  HTTP_NOT_IMPLEMENTED(501, "This feature is not yet implemented"),
  HTTP_BAD_GATEWAY(502, "The server received an invalid response"),
  HTTP_SERVICE_UNAVAILABLE(503, "The service is temporarily unavailable"),
  HTTP_GATEWAY_TIMEOUT(504, "The server timed out waiting for a response"),

  // Validation Errors (1xxx)
  VALIDATION_ERROR(1000, "One or more validation errors occurred"),
  VALIDATION_REQUIRED_FIELD(1001, "This field is required"),
  VALIDATION_MISSING_FIELD(1002, "A required field is missing from the request"),
  VALIDATION_INVALID_EMAIL(1003, "Please enter a valid email address"),
  VALIDATION_INVALID_PHONE(1004, "Please enter a valid phone number"),
  VALIDATION_INVALID_DATE(1005, "Please enter a valid date"),
  VALIDATION_INVALID_FORMAT(1006, "The provided format is incorrect"),
  VALIDATION_INVALID_PARAMETER(1007, "One or more parameters are invalid"),
  VALIDATION_MALFORMED_REQUEST(1008, "The request format is invalid or malformed"),

  // Business Errors (2xxx)
  BUSINESS_DUPLICATE_ENTRY(2000, "This entry already exists"),
  BUSINESS_INVALID_STATE(2001, "Invalid state for this operation"),
  BUSINESS_INVALID_OPERATION(2002, "The requested operation is invalid in the current state"),
  BUSINESS_INVALID_TRANSITION(2003, "Cannot transition to the requested state"),
  BUSINESS_EXPIRED_RESOURCE(2004, "This resource has expired"),
  BUSINESS_RULE_VIOLATION(2005, "This operation violates business rules"),

  // Security Errors (3xxx)
  SECURITY_TOKEN_EXPIRED(3000, "Your session has expired. Please log in again"),
  SECURITY_INVALID_CREDENTIALS(3001, "Invalid username or password"),
  SECURITY_ACCOUNT_LOCKED(3002, "Account has been locked. Please contact support"),
  SECURITY_INVALID_TOKEN(3003, "Invalid or malformed authentication token"),
  SECURITY_PASSWORD_EXPIRED(3004, "Password has expired. Please reset your password"),
  SECURITY_INSUFFICIENT_PERMISSIONS(3005, "You don't have sufficient permissions"),
  SECURITY_INVALID_2FA_CODE(3006, "Invalid two-factor authentication code"),

  // Data Errors (4xxx)
  DATA_INTEGRITY_ERROR(4000, "Data integrity constraint has been violated"),
  DATA_INTEGRITY_VIOLATION(4001, "Unable to process due to data integrity constraints"),
  DATA_STALE(4002, "The data has been modified. Please refresh and try again"),
  DATA_NOT_FOUND(4003, "Required data was not found"),
  DATA_ALREADY_EXISTS(4004, "This data already exists"),
  DATA_CORRUPTED(4005, "The data appears to be corrupted"),
  DATA_INVALID_REFERENCE(4006, "Invalid reference to related data"),

  // Integration Errors (5xxx)
  INTEGRATION_EXTERNAL_SERVICE_ERROR(5000, "External service error occurred"),
  INTEGRATION_INTEGRATION_TIMEOUT(5001, "Integration request timed out"),
  INTEGRATION_INVALID_RESPONSE(5002, "Received invalid response from external service"),
  INTEGRATION_SERVICE_UNAVAILABLE_ERROR(5003, "External service is currently unavailable"),
  INTEGRATION_API_LIMIT_EXCEEDED(5004, "External API rate limit exceeded"),

  // System Errors (9xxx)
  SYSTEM_ERROR(9000, "A system error has occurred"),
  SYSTEM_CONFIGURATION_ERROR(9001, "System configuration error"),
  SYSTEM_RESOURCE_EXHAUSTED(9002, "System resources are exhausted"),
  SYSTEM_MAINTENANCE_MODE(9003, "System is under maintenance"),
  SYSTEM_CRITICAL_ERROR(9004, "A critical system error has occurred");

  private final int code;
  private final String message;

  /**
   * Gets the category of the error code based on its numerical value.
   *
   * @return The category of the error code
   */
  public ErrorCategory getCategory() {
    if (name().startsWith("HTTP_")) {
      return ErrorCategory.HTTP;
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
   * Gets the HTTP status code category for this error.
   *
   * @return the HTTP status code (e.g., 400, 404, 500)
   */
  public int getHttpStatus() {
    if (code >= 400 && code < 600) {
      return code;
    }
    // For non-HTTP error codes, map them to appropriate HTTP status
    if (code >= 1000 && code < 2000) {
      return 400; // Validation errors -> Bad Request
    }
    if (code >= 2000 && code < 3000) {
      return 422; // Business errors -> Unprocessable Entity
    }
    if (code >= 3000 && code < 4000) {
      return 403; // Security errors -> Forbidden
    }
    if (code >= 4000 && code < 5000) {
      return 409; // Data errors -> Conflict
    }
    if (code >= 5000 && code < 6000) {
      return 502; // Integration errors -> Bad Gateway
    }
    return 500; // System errors -> Internal Server Error
  }

  /**
   * Checks if the error code represents a client error (4xx).
   *
   * @return true if it's a client error, false otherwise
   */
  public boolean isClientError() {
    int httpStatus = getHttpStatus();
    return httpStatus >= 400 && httpStatus < 500;
  }

  /**
   * Checks if the error code represents a server error (5xx).
   *
   * @return true if it's a server error, false otherwise
   */
  public boolean isServerError() {
    int httpStatus = getHttpStatus();
    return httpStatus >= 500 && httpStatus < 600;
  }
}