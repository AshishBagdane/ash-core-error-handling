package com.ashishbagdane.lib.eh.validation.validators;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationError;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;
import com.ashishbagdane.lib.eh.exception.validation.validators.EmailFieldValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailFieldValidatorTest {

    // Test class to represent a user with email
    private static class TestUser {

        private final String email;

        TestUser(String email) {
            this.email = email;
        }

        String getEmail() {
            return email;
        }
    }

    @Test
    void constructor_ShouldInitializeCorrectly() {
        // Arrange & Act
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "email"
        );

        // Assert
        assertNotNull(validator);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "test@example.com",
        "test.name@example.com",
        "test+label@example.com",
        "test@subdomain.example.com",
        "123@example.com",
        "test@example-domain.com",
        "test_name@example.com",
        "test.name+label@example.com"
    })
    void validate_ShouldReturnValid_ForValidEmails(String email) {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "email"
        );
        TestUser user = new TestUser(email);

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",                    // Empty string
        "test",               // No @ symbol
        "@example.com",       // No local part
        "test@",             // No domain
        "test@.com",         // Domain starts with dot
        "test@domain.",      // Domain ends with dot
        "test@@domain.com",  // Multiple @ symbols
        "test@domain",       // No top-level domain
        "test..name@domain.com", // Consecutive dots
        " test@domain.com",  // Leading space
        "test@domain.com ",  // Trailing space
        "test#@domain.com"   // Invalid special character
    })
    void validate_ShouldReturnInvalid_ForInvalidEmails(String email) {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "emailField"
        );
        TestUser user = new TestUser(email);

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals(ErrorCode.VALIDATION_INVALID_EMAIL, error.getErrorCode());
        assertEquals("Invalid email format for field emailField", error.getMessage());
    }

    @ParameterizedTest
    @NullSource
    void validate_ShouldReturnInvalid_ForNullEmail(String email) {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "emailField"
        );
        TestUser user = new TestUser(email);

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals(ErrorCode.VALIDATION_INVALID_EMAIL, error.getErrorCode());
        assertEquals("Invalid email format for field emailField", error.getMessage());
    }

    @Test
    void validate_ShouldHandleNullEmail() {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "emailField"
        );
        TestUser user = new TestUser(null);

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals(ErrorCode.VALIDATION_INVALID_EMAIL, error.getErrorCode());
        assertEquals("Invalid email format for field emailField", error.getMessage());
    }

    @Test
    void validate_WithNullObject_ShouldThrowNullPointerException() {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            "emailField"
        );

        // Act & Assert
        assertThrows(NullPointerException.class, () -> validator.validate(null));
    }

    @Test
    void validate_ShouldUseProvidedFieldName() {
        // Arrange
        String customFieldName = "customEmailField";
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            TestUser::getEmail,
            customFieldName
        );
        TestUser user = new TestUser("invalid-email");

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertTrue(error.getMessage().contains(customFieldName));
    }

    @Test
    void validate_ShouldHandleFieldExtractorReturningNull() {
        // Arrange
        EmailFieldValidator<TestUser> validator = new EmailFieldValidator<>(
            user -> null,  // Field extractor always returns null
            "emailField"
        );
        TestUser user = new TestUser("test@example.com");

        // Act
        ValidationResult result = validator.validate(user);

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        ValidationError error = result.getErrors().get(0);
        assertEquals(ErrorCode.VALIDATION_INVALID_EMAIL, error.getErrorCode());
    }
}
