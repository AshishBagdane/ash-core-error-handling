package com.ashishbagdane.lib.eh.exception.validation.validators;

import com.ashishbagdane.lib.eh.exception.validation.api.ValidationError;
import com.ashishbagdane.lib.eh.exception.validation.api.ValidationResult;
import com.ashishbagdane.lib.eh.exception.validation.api.Validator;
import com.ashishbagdane.lib.eh.exception.validation.base.CompositeValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Chains multiple validators together with configurable validation behavior. Supports both fail-fast and collect-all
 * validation strategies.
 *
 * @param <T> The type of object to be validated
 * @since 1.0
 */
@Component
public class ChainedValidator<T> extends CompositeValidator<T> {

    private final ValidationType validationType;

    /**
     * Defines the validation strategy to be used when processing multiple validators.
     */
    public enum ValidationType {
        /**
         * Stops validation process at the first encountered error
         */
        FAIL_FAST,
        /**
         * Continues validation to collect all possible errors
         */
        VALIDATE_ALL
    }

    @SafeVarargs
    public ChainedValidator(ValidationType validationType, Validator<T>... validators) {
        this.validationType = validationType;
        Arrays.stream(validators).forEach(this::addValidator);
    }

    @Override
    public ValidationResult validate(T input) {
        List<ValidationError> errors = new ArrayList<>();

        for (Validator<T> validator : getValidators()) {
            ValidationResult result = validator.validate(input);

            if (result.isInvalid()) {
                errors.addAll(result.getErrors());

                if (validationType == ValidationType.FAIL_FAST) {
                    break;
                }
            }
        }

        return errors.isEmpty() ? ValidationResult.valid() :
            ValidationResult.invalid(errors);
    }

    public List<Validator<T>> getValidators() {
        return Collections.unmodifiableList(validators);
    }
}
