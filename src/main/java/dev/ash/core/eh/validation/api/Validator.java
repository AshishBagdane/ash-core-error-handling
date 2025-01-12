package dev.ash.core.eh.validation.api;

/**
 * Core interface for performing validations on input objects.
 *
 * @param <T> The type of object to be validated
 * @since 1.0
 */
public interface Validator<T> {

  /**
   * Validates the input object according to defined validation rules.
   *
   * @param input The object to validate
   * @return A {@link ValidationResult} containing validation status and any errors
   * @throws IllegalArgumentException if input is null
   */
  ValidationResult validate(T input);
}