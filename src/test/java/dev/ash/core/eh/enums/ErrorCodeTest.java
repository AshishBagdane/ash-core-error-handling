package dev.ash.core.eh.enums;

import dev.ash.core.eh.core.ErrorCategory;
import dev.ash.core.eh.core.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ErrorCode Tests")
class ErrorCodeTest {

    @Test
    @DisplayName("Should have unique error codes")
    void shouldHaveUniqueErrorCodes() {
        Set<Integer> codes = new HashSet<>();

        for (ErrorCode errorCode : ErrorCode.values()) {
            assertTrue(codes.add(errorCode.getCode()),
                       "Duplicate code found: " + errorCode.getCode());
        }
    }

    @Test
    @DisplayName("Should have non-null and non-empty messages")
    void shouldHaveValidMessages() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertNotNull(errorCode.getDefaultMessage(),
                          "Message should not be null for " + errorCode.name());
            assertFalse(errorCode.getDefaultMessage().trim().isEmpty(),
                        "Message should not be empty for " + errorCode.name());
        }
    }

    @Test
    @DisplayName("Should maintain code ranges for each category")
    void shouldMaintainCodeRanges() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            ErrorCategory category = errorCode.getCategory();
            assertTrue(category.contains(errorCode.getCode()),
                       String.format("Error code %s (%d) is outside its category %s range",
                                     errorCode.name(), errorCode.getCode(), category));
        }
    }

    @ParameterizedTest
    @DisplayName("Should map to correct HTTP status")
    @MethodSource("provideMappingTestCases")
    void shouldMapToCorrectHttpStatus(ErrorCode errorCode, int expectedHttpStatus) {
        assertEquals(expectedHttpStatus, errorCode.getHttpStatus().value());
    }

    private static Stream<Arguments> provideMappingTestCases() {
        return Stream.of(
            Arguments.of(ErrorCode.VALIDATION_ERROR, 400),
            Arguments.of(ErrorCode.BUSINESS_RULE_VIOLATION, 422),
            Arguments.of(ErrorCode.HTTP_FORBIDDEN, 403),
            Arguments.of(ErrorCode.DATA_INTEGRITY_ERROR, 409),
            Arguments.of(ErrorCode.SYSTEM_ERROR, 500)
        );
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify client errors")
    @EnumSource(ErrorCode.class)
    void shouldIdentifyClientErrors(ErrorCode errorCode) {
        int httpStatus = errorCode.getHttpStatus().value();
        assertEquals(httpStatus >= 400 && httpStatus < 500,
                     errorCode.isClientError());
    }

    @ParameterizedTest
    @DisplayName("Should correctly identify server errors")
    @EnumSource(ErrorCode.class)
    void shouldIdentifyServerErrors(ErrorCode errorCode) {
        int httpStatus = errorCode.getHttpStatus().value();
        assertEquals(httpStatus >= 500 && httpStatus < 600,
                     errorCode.isServerError());
    }

    @Test
    @DisplayName("Should follow naming convention")
    void shouldFollowNamingConvention() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            assertEquals(errorCode.name(), errorCode.name().toUpperCase(), "Error code names should be uppercase");
            assertFalse(errorCode.name().contains(" "),
                        "Error code names should not contain spaces");
        }
    }

    @Test
    @DisplayName("Should have consistent message formatting")
    void shouldHaveConsistentMessageFormatting() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            String message = errorCode.getDefaultMessage();
            assertTrue(Character.isUpperCase(message.charAt(0)),
                       "Message should start with capital letter");
            assertFalse(message.endsWith("."),
                        "Message should not end with period");
            assertEquals(message.trim(), message, "Message should not have leading/trailing spaces");
        }
    }

    @Test
    @DisplayName("Categories should have consistent code ranges")
    void categoriesShouldHaveConsistentCodeRanges() {
        assertAll(
            () -> assertTrue(ErrorCode.VALIDATION_ERROR.getCode() >= 1000
                                 && ErrorCode.VALIDATION_ERROR.getCode() < 2000),
            () -> assertTrue(ErrorCode.BUSINESS_RULE_VIOLATION.getCode() >= 2000
                                 && ErrorCode.BUSINESS_RULE_VIOLATION.getCode() < 3000),
            () -> assertTrue(ErrorCode.SECURITY_TOKEN_EXPIRED.getCode() >= 3000
                                 && ErrorCode.SECURITY_TOKEN_EXPIRED.getCode() < 4000),
            () -> assertTrue(ErrorCode.DATA_INTEGRITY_ERROR.getCode() >= 4000
                                 && ErrorCode.DATA_INTEGRITY_ERROR.getCode() < 5000)
        );
    }

    @Test
    @DisplayName("Should have consistent prefix naming")
    void shouldHaveConsistentPrefixNaming() {
        for (ErrorCode errorCode : ErrorCode.values()) {
            ErrorCategory category = errorCode.getCategory();
            if (category != ErrorCategory.HTTP_STANDARD) {
                assertTrue(errorCode.name().startsWith(category.name()),
                           String.format("Error code %s should start with its category prefix %s",
                                         errorCode.name(), category.name()));
            } else {
                assertTrue(errorCode.name().startsWith("HTTP_"),
                           "HTTP error codes should start with HTTP_");
            }
        }
    }
}
