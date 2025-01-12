package dev.ash.core.eh.validation.validators;

import dev.ash.core.eh.enums.ErrorCode;
import dev.ash.core.eh.validation.api.ValidationResult;
import dev.ash.core.eh.validation.base.FieldValidator;
import java.util.Collections;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

/**
 * Validates email addresses according to a standard email format pattern. Can be applied to any object type that contains an email field.
 *
 * @param <T> The type of object containing the email field
 * @since 1.0
 */
@Component
public class EmailFieldValidator<T> extends FieldValidator<T, String> {

  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9]+(?:[._+-]?[A-Za-z0-9]+)*@[A-Za-z0-9][A-Za-z0-9-]*(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

  public EmailFieldValidator(Function<T, String> fieldExtractor, String fieldName) {
    super(fieldExtractor, fieldName);
  }

  @Override
  protected ValidationResult validateField(String email) {
    if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
      return ValidationResult.invalid(Collections.singletonList(
          createError(ErrorCode.VALIDATION_INVALID_EMAIL,
              String.format("Invalid email format for field %s", getFieldName()))
      ));
    }
    return ValidationResult.valid();
  }
}