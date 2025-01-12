package dev.ash.core.eh.dto;

import dev.ash.core.eh.enums.ErrorCode;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an error message containing error details and associated metadata. This record is immutable except for the ability to add developer messages.
 */
public record ErrorMessage(
    ErrorCode errorCode,
    Set<DeveloperErrorMessage> developerMessages
) {

  /**
   * Constructs an ErrorMessage with the specified error code. Initializes an empty set for developer messages.
   *
   * @param errorCode The enum representing the error
   */
  public ErrorMessage(ErrorCode errorCode) {
    this(errorCode, new HashSet<>());
  }

  /**
   * Adds a developer error message to the collection.
   *
   * @param developerMessage The developer error message to add
   * @return A new ErrorMessage instance with the updated developer messages
   */
  public ErrorMessage addDeveloperMessage(DeveloperErrorMessage developerMessage) {
    Set<DeveloperErrorMessage> updatedMessages = new HashSet<>(this.developerMessages);
    updatedMessages.add(developerMessage);
    return new ErrorMessage(this.errorCode, updatedMessages);
  }

  /**
   * Adds a developer message using an error code.
   *
   * @param errorCode The error code to add as a developer message
   * @return A new ErrorMessage instance with the updated developer messages
   */
  public ErrorMessage addDeveloperMessage(ErrorCode errorCode) {
    return addDeveloperMessage(new DeveloperErrorMessage(errorCode));
  }

  /**
   * Checks if there are any developer error messages.
   *
   * @return true if there are developer messages, false otherwise
   */
  public boolean hasErrors() {
    return !developerMessages.isEmpty();
  }

  /**
   * Gets the error code value.
   *
   * @return the numeric error code
   */
  public int getCode() {
    return errorCode.getCode();
  }

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  public String getMessage() {
    return errorCode.getMessage();
  }

  /**
   * Creates a formatted string representation of the error message.
   *
   * @return A formatted string containing all error details
   */
  @Override
  public String toString() {
    return """
        Error Details:
        Code: %d
        Message: %s
        Developer Messages: %s""".formatted(
        getCode(),
        getMessage(),
        developerMessages.isEmpty() ? "None" : developerMessages
    );
  }
}