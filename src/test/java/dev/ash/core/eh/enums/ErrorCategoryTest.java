package dev.ash.core.eh.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ErrorCategoryTest {

  @Test
  void testEnumValues() {
    ErrorCategory[] categories = ErrorCategory.values();
    assertEquals(7, categories.length);
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.HTTP));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.VALIDATION));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.BUSINESS));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.SECURITY));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.DATA));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.INTEGRATION));
    assertTrue(Stream.of(categories).anyMatch(c -> c == ErrorCategory.SYSTEM));
  }

  @Test
  void testRangeProperties() {
    // Test min and max code values for each category
    assertEquals(400, ErrorCategory.HTTP.getMinCode());
    assertEquals(599, ErrorCategory.HTTP.getMaxCode());
    assertEquals(1000, ErrorCategory.VALIDATION.getMinCode());
    assertEquals(1999, ErrorCategory.VALIDATION.getMaxCode());
    assertEquals(2000, ErrorCategory.BUSINESS.getMinCode());
    assertEquals(2999, ErrorCategory.BUSINESS.getMaxCode());
    assertEquals(3000, ErrorCategory.SECURITY.getMinCode());
    assertEquals(3999, ErrorCategory.SECURITY.getMaxCode());
    assertEquals(4000, ErrorCategory.DATA.getMinCode());
    assertEquals(4999, ErrorCategory.DATA.getMaxCode());
    assertEquals(5000, ErrorCategory.INTEGRATION.getMinCode());
    assertEquals(5999, ErrorCategory.INTEGRATION.getMaxCode());
    assertEquals(9000, ErrorCategory.SYSTEM.getMinCode());
    assertEquals(9999, ErrorCategory.SYSTEM.getMaxCode());
  }

  @ParameterizedTest
  @MethodSource("validCodeProvider")
  void testValidCodes(ErrorCategory category, int code) {
    assertTrue(category.contains(code));
  }

  private static Stream<Arguments> validCodeProvider() {
    return Stream.of(
        Arguments.of(ErrorCategory.HTTP, 400),        // Lower bound
        Arguments.of(ErrorCategory.HTTP, 500),        // Middle value
        Arguments.of(ErrorCategory.HTTP, 599),        // Upper bound
        Arguments.of(ErrorCategory.VALIDATION, 1000), // Lower bound
        Arguments.of(ErrorCategory.VALIDATION, 1500), // Middle value
        Arguments.of(ErrorCategory.VALIDATION, 1999),  // Upper bound
        Arguments.of(ErrorCategory.BUSINESS, 2000), // Lower bound
        Arguments.of(ErrorCategory.BUSINESS, 2500), // Middle value
        Arguments.of(ErrorCategory.BUSINESS, 2999),  // Upper bound
        Arguments.of(ErrorCategory.SECURITY, 3000), // Lower bound
        Arguments.of(ErrorCategory.SECURITY, 3500), // Middle value
        Arguments.of(ErrorCategory.SECURITY, 3999),  // Upper bound
        Arguments.of(ErrorCategory.DATA, 4000), // Lower bound
        Arguments.of(ErrorCategory.DATA, 4500), // Middle value
        Arguments.of(ErrorCategory.DATA, 4999),  // Upper bound
        Arguments.of(ErrorCategory.INTEGRATION, 5000), // Lower bound
        Arguments.of(ErrorCategory.INTEGRATION, 5500), // Middle value
        Arguments.of(ErrorCategory.INTEGRATION, 5999),  // Upper bound
        Arguments.of(ErrorCategory.SYSTEM, 9000), // Lower bound
        Arguments.of(ErrorCategory.SYSTEM, 9500), // Middle value
        Arguments.of(ErrorCategory.SYSTEM, 9999)  // Upper bound
    );
  }

  @ParameterizedTest
  @MethodSource("invalidCodeProvider")
  void testInvalidCodes(ErrorCategory category, int code) {
    assertFalse(category.contains(code));
  }

  private static Stream<Arguments> invalidCodeProvider() {
    return Stream.of(
        Arguments.of(ErrorCategory.HTTP, 399),        // Just below range
        Arguments.of(ErrorCategory.HTTP, 600),        // Just above range
        Arguments.of(ErrorCategory.VALIDATION, 999),  // Just below range
        Arguments.of(ErrorCategory.VALIDATION, 2000), // Just above range
        Arguments.of(ErrorCategory.HTTP, 1500),       // Valid for different category
        Arguments.of(ErrorCategory.VALIDATION, 500),  // Valid for different category
        Arguments.of(ErrorCategory.BUSINESS, 1999),        // Just below range
        Arguments.of(ErrorCategory.BUSINESS, 3000),        // Just above range
        Arguments.of(ErrorCategory.INTEGRATION, 2999),  // Just below range
        Arguments.of(ErrorCategory.INTEGRATION, 4000), // Just above range
        Arguments.of(ErrorCategory.DATA, 3999),        // Just below range
        Arguments.of(ErrorCategory.DATA, 5000),        // Just above range
        Arguments.of(ErrorCategory.SECURITY, 4999),  // Just below range
        Arguments.of(ErrorCategory.SECURITY, 6000), // Just above range
        Arguments.of(ErrorCategory.SYSTEM, 8999),        // Just below range
        Arguments.of(ErrorCategory.SYSTEM, 10000)        // Just above range
    );
  }

  @Test
  void testNoOverlappingRanges() {
    ErrorCategory[] categories = ErrorCategory.values();
    for (int i = 0; i < categories.length; i++) {
      for (int j = i + 1; j < categories.length; j++) {
        ErrorCategory cat1 = categories[i];
        ErrorCategory cat2 = categories[j];

        // Test no overlap in ranges
        assertFalse(
            cat1.getMaxCode() >= cat2.getMinCode() && cat1.getMinCode() <= cat2.getMaxCode(),
            String.format("Categories %s and %s have overlapping ranges", cat1, cat2)
        );
      }
    }
  }

  @Test
  void testRangeConsistency() {
    for (ErrorCategory category : ErrorCategory.values()) {
      assertTrue(category.getMaxCode() > category.getMinCode(),
          String.format("Category %s has invalid range: min=%d, max=%d",
              category, category.getMinCode(), category.getMaxCode()));
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {Integer.MIN_VALUE, -1, Integer.MAX_VALUE})
  void testExtremeCases(int code) {
    // Verify that extreme values don't belong to any category
    for (ErrorCategory category : ErrorCategory.values()) {
      assertFalse(category.contains(code),
          String.format("Category %s should not contain extreme value %d", category, code));
    }
  }
}