package dev.ash.core.eh.validation.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.ash.core.eh.enums.ErrorCode;
import dev.ash.core.eh.validation.api.ValidationResult;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RequiredFieldValidatorTest {

  private static class TestObject {

    private final String stringField;
    private final List<String> listField;

    TestObject(String stringField, List<String> listField) {
      this.stringField = stringField;
      this.listField = listField;
    }
  }

  private RequiredFieldValidator<TestObject, String> stringValidator;
  private RequiredFieldValidator<TestObject, List<String>> listValidator;

  @BeforeEach
  void setUp() {
    stringValidator = new RequiredFieldValidator<>(obj -> obj.stringField, "stringField");
    listValidator = new RequiredFieldValidator<>(obj -> obj.listField, "listField");
  }

  @Test
  void constructor_shouldThrowException_whenFieldExtractorIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> new RequiredFieldValidator<TestObject, String>(null, "field"));
  }

  @Test
  void constructor_shouldThrowException_whenFieldNameIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> new RequiredFieldValidator<TestObject, String>(obj -> obj.stringField, null));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {" ", "  \t  \n"})
  void validateField_shouldReturnInvalid_forNullOrEmptyStrings(String input) {
    TestObject testObj = new TestObject(input, Collections.emptyList());
    ValidationResult result = stringValidator.validate(testObj);

    assertFalse(result.isValid());
    assertEquals(1, result.getErrors().size());
    assertEquals(ErrorCode.VALIDATION_MISSING_FIELD, result.getErrors().get(0).getErrorCode());
  }

  @Test
  void validateField_shouldReturnValid_forNonEmptyString() {
    TestObject testObj = new TestObject("valid", Collections.emptyList());
    ValidationResult result = stringValidator.validate(testObj);

    assertTrue(result.isValid());
    assertTrue(result.getErrors().isEmpty());
  }

  @Test
  void validateField_shouldReturnInvalid_forNullList() {
    TestObject testObj = new TestObject("valid", null);
    ValidationResult result = listValidator.validate(testObj);

    assertFalse(result.isValid());
  }

  @Test
  void validateField_shouldReturnInvalid_forEmptyList() {
    TestObject testObj = new TestObject("valid", Collections.emptyList());
    ValidationResult result = listValidator.validate(testObj);

    assertFalse(result.isValid());
  }

  @Test
  void validateField_shouldReturnValid_forNonEmptyList() {
    TestObject testObj = new TestObject("valid", List.of("item"));
    ValidationResult result = listValidator.validate(testObj);

    assertTrue(result.isValid());
  }

  @Test
  void validateField_shouldReturnValid_forNonEmptyMap() {
    RequiredFieldValidator<Map<String, String>, Map<String, String>> mapValidator =
        new RequiredFieldValidator<>(map -> map, "mapField");

    Map<String, String> testMap = new HashMap<>();
    testMap.put("key", "value");

    ValidationResult result = mapValidator.validate(testMap);
    assertTrue(result.isValid());
  }

  @Test
  void validateField_shouldReturnInvalid_forEmptyMap() {
    RequiredFieldValidator<Map<String, String>, Map<String, String>> mapValidator =
        new RequiredFieldValidator<>(map -> map, "mapField");

    ValidationResult result = mapValidator.validate(new HashMap<>());
    assertFalse(result.isValid());
  }
}