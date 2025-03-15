package com.ashishbagdane.lib.eh.validation.validators;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationError;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;
import com.ashishbagdane.lib.eh.exception.validation.api.Validator;
import com.ashishbagdane.lib.eh.exception.validation.base.DefaultValidationError;
import com.ashishbagdane.lib.eh.exception.validation.validators.ChainedValidator;
import com.ashishbagdane.lib.eh.exception.validation.validators.ChainedValidator.ValidationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChainedValidatorTest {

    @Test
    void constructor_ShouldInitializeWithValidatorsAndType() {
        // Arrange
        @SuppressWarnings("unchecked")
        Validator<String> validator1 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator2 = mock(Validator.class);

        // Act
        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL,
            validator1,
            validator2
        );

        // Assert
        List<Validator<String>> validators = chainedValidator.getValidators();
        assertEquals(2, validators.size());
        assertTrue(validators.contains(validator1));
        assertTrue(validators.contains(validator2));
    }

    @Test
    void validate_ShouldReturnValid_WhenAllValidatorsPass() {
        // Arrange
        @SuppressWarnings("unchecked")
        Validator<String> validator1 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator2 = mock(Validator.class);

        when(validator1.validate(any(String.class))).thenReturn(ValidationResult.valid());
        when(validator2.validate(any(String.class))).thenReturn(ValidationResult.valid());

        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL,
            validator1,
            validator2
        );

        // Act
        ValidationResult result = chainedValidator.validate("test");

        // Assert
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void validate_WithFailFast_ShouldStopAtFirstError() {
        // Arrange
        ValidationError error1 = new DefaultValidationError(ErrorCode.VALIDATION_ERROR);
        ValidationError error2 = new DefaultValidationError(ErrorCode.VALIDATION_INVALID_EMAIL);

        @SuppressWarnings("unchecked")
        Validator<String> validator1 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator2 = mock(Validator.class);

        when(validator1.validate(any(String.class))).thenReturn(ValidationResult.invalid(List.of(error1)));
        when(validator2.validate(any(String.class))).thenReturn(ValidationResult.invalid(List.of(error2)));

        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.FAIL_FAST,
            validator1,
            validator2
        );

        // Act
        ValidationResult result = chainedValidator.validate("test");

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(error1, result.getErrors().get(0));
        verify(validator2, never()).validate(any(String.class)); // Second validator should not be called
    }

    @Test
    void validate_WithValidateAll_ShouldCollectAllErrors() {
        // Arrange
        ValidationError error1 = new DefaultValidationError(ErrorCode.VALIDATION_ERROR);
        ValidationError error2 = new DefaultValidationError(ErrorCode.VALIDATION_INVALID_EMAIL);

        @SuppressWarnings("unchecked")
        Validator<String> validator1 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator2 = mock(Validator.class);

        when(validator1.validate(any(String.class))).thenReturn(ValidationResult.invalid(List.of(error1)));
        when(validator2.validate(any(String.class))).thenReturn(ValidationResult.invalid(List.of(error2)));

        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL,
            validator1,
            validator2
        );

        // Act
        ValidationResult result = chainedValidator.validate("test");

        // Assert
        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size());
        assertTrue(result.getErrors().contains(error1));
        assertTrue(result.getErrors().contains(error2));
    }

    @Test
    void validate_WithMixedResults_ShouldHandleCorrectly() {
        // Arrange
        ValidationError error = new DefaultValidationError(ErrorCode.VALIDATION_ERROR);

        @SuppressWarnings("unchecked")
        Validator<String> validator1 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator2 = mock(Validator.class);
        @SuppressWarnings("unchecked")
        Validator<String> validator3 = mock(Validator.class);

        when(validator1.validate(any(String.class))).thenReturn(ValidationResult.valid());
        when(validator2.validate(any(String.class))).thenReturn(ValidationResult.invalid(List.of(error)));
        when(validator3.validate(any(String.class))).thenReturn(ValidationResult.valid());

        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL,
            validator1,
            validator2,
            validator3
        );

        // Act
        ValidationResult result = chainedValidator.validate("test");

        // Assert
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals(error, result.getErrors().get(0));
    }

    @Test
    void getValidators_ShouldReturnUnmodifiableList() {
        // Arrange
        @SuppressWarnings("unchecked")
        Validator<String> validator = mock(Validator.class);
        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL,
            validator
        );

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () ->
            chainedValidator.getValidators().add(mock(Validator.class))
        );
    }

    @Test
    void validate_WithNoValidators_ShouldReturnValid() {
        // Arrange
        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            ValidationType.VALIDATE_ALL
        );

        // Act
        ValidationResult result = chainedValidator.validate("test");

        // Assert
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(ValidationType.class)
    void validate_ShouldHandleNullInput(ValidationType validationType) {
        // Arrange
        @SuppressWarnings("unchecked")
        Validator<String> validator = mock(Validator.class);
        when(validator.validate(null)).thenReturn(ValidationResult.valid());

        ChainedValidator<String> chainedValidator = new ChainedValidator<>(
            validationType,
            validator
        );

        // Act
        ValidationResult result = chainedValidator.validate(null);

        // Assert
        assertTrue(result.isValid());
        verify(validator).validate(null);
    }
}
