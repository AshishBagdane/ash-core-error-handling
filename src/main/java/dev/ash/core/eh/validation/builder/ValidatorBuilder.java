package dev.ash.core.eh.validation.builder;

import dev.ash.core.eh.validation.api.Validator;
import dev.ash.core.eh.validation.base.CompositeValidator;
import dev.ash.core.eh.validation.validators.EmailFieldValidator;
import dev.ash.core.eh.validation.validators.RequiredFieldValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Builder for creating validator chains with a fluent API. Provides convenience methods for adding common validators.
 *
 * @param <T> The type of object to be validated
 * @since 1.0
 */
public class ValidatorBuilder<T> {

  private final List<Validator<T>> validators = new ArrayList<>();

  /**
   * Adds a custom validator to the chain.
   *
   * @param validator The validator to add
   * @return This builder instance for method chaining
   */
  public ValidatorBuilder<T> addValidator(Validator<T> validator) {
    validators.add(validator);
    return this;
  }

  /**
   * Adds an email validation for the specified field.
   *
   * @param emailExtractor Function to extract the email field
   * @param fieldName      Name of the field for error messages
   * @return This builder instance for method chaining
   */
  public ValidatorBuilder<T> validateEmail(Function<T, String> emailExtractor, String fieldName) {
    validators.add(new EmailFieldValidator<>(emailExtractor, fieldName));
    return this;
  }

  /**
   * Adds a required field validation for the specified field.
   *
   * @param fieldExtractor Function to extract the field
   * @param fieldName      Name of the field for error messages
   * @return This builder instance for method chaining
   */
  public ValidatorBuilder<T> validateRequired(Function<T, ?> fieldExtractor, String fieldName) {
    validators.add(new RequiredFieldValidator<>(fieldExtractor, fieldName));
    return this;
  }

  public Validator<T> build() {
    return new CompositeValidator<T>() {
      {
        validators.forEach(this::addValidator);
      }
    };
  }
}