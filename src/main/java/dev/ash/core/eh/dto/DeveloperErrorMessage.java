package dev.ash.core.eh.dto;

import dev.ash.core.eh.enums.ErrorCode;
import java.time.Instant;

/**
 * Represents a detailed error message intended for developers, containing error code, message, affected field, and additional developer-specific information with timestamp.
 *
 * @param code             The error code as a string
 * @param message          The general error message
 * @param messageField     The specific field associated with the error (optional)
 * @param developerMessage Additional technical details for developers (optional)
 * @param timestamp        The instant when the error message was created
 */
public record DeveloperErrorMessage(
    String code,
    String message,
    String messageField,
    String developerMessage,
    Instant timestamp
) {

  /**
   * Primary constructor with validation.
   */
  public DeveloperErrorMessage {
    if (code == null) {
      throw new IllegalArgumentException("Error code cannot be null");
    }
    if (message == null) {
      throw new IllegalArgumentException("Error message cannot be null");
    }
    if (timestamp == null) {
      timestamp = Instant.now();
    }
  }

  /**
   * Constructs a DeveloperErrorMessage with just code and message.
   *
   * @param code    The error code
   * @param message The error message
   */
  public DeveloperErrorMessage(String code, String message) {
    this(code, message, null, null, Instant.now());
  }

  /**
   * Constructs a DeveloperErrorMessage from an ErrorCode.
   *
   * @param errorCode The error code enum value
   */
  public DeveloperErrorMessage(ErrorCode errorCode) {
    this(String.valueOf(errorCode.getCode()),
        errorCode.getMessage(),
        null,
        null,
        Instant.now());
  }

  /**
   * Constructs a DeveloperErrorMessage from an ErrorCode with a specific message field.
   *
   * @param errorCode    The error code enum value
   * @param messageField The field associated with the error
   */
  public DeveloperErrorMessage(ErrorCode errorCode, String messageField) {
    this(String.valueOf(errorCode.getCode()),
        errorCode.getMessage(),
        messageField,
        null,
        Instant.now());
  }

  /**
   * Constructs a DeveloperErrorMessage from an ErrorCode with a message field and developer message.
   *
   * @param errorCode        The error code enum value
   * @param messageField     The field associated with the error
   * @param developerMessage Additional technical details for developers
   */
  public DeveloperErrorMessage(ErrorCode errorCode, String messageField, String developerMessage) {
    this(String.valueOf(errorCode.getCode()),
        errorCode.getMessage(),
        messageField,
        developerMessage,
        Instant.now());
  }

  /**
   * Creates a builder-style instance with a field error.
   *
   * @param field   The field name
   * @param message The error message
   * @return A new DeveloperErrorMessage instance
   */
  public static DeveloperErrorMessage fieldError(String field, String message) {
    return new DeveloperErrorMessage(
        "FIELD_VALIDATION_ERROR",
        message,
        field,
        null,
        Instant.now()
    );
  }

  /**
   * Creates a copy of this message with an additional developer message.
   *
   * @param additionalInfo The additional developer information
   * @return A new DeveloperErrorMessage instance with the added information
   */
  public DeveloperErrorMessage withDeveloperMessage(String additionalInfo) {
    return new DeveloperErrorMessage(
        this.code,
        this.message,
        this.messageField,
        additionalInfo,
        Instant.now()
    );
  }

  /**
   * Returns a formatted string representation of the error message.
   *
   * @return A formatted string containing all error details
   */
  @Override
  public String toString() {
    return """
        %s%s%s%s""".formatted(
        message,
        messageField != null ? " (Field: " + messageField + ")" : "",
        developerMessage != null ? " - " + developerMessage : "",
        " [Code: " + code + "]"
    );
  }
}