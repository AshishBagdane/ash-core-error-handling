package dev.ash.core.eh.exception.validation.base;

import dev.ash.core.eh.exception.validation.api.ValidationError;
import dev.ash.core.eh.exception.validation.api.ValidationResult;
import dev.ash.core.eh.exception.validation.api.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Composite validator that can combine multiple validators into a single validation chain. Supports adding multiple
 * validators and executing them in sequence.
 *
 * @param <T> The type of object to be validated
 * @since 1.0
 */
public abstract class CompositeValidator<T> extends BaseValidator<T> {

    protected final List<Validator<T>> validators = new ArrayList<>();

    protected void addValidator(Validator<T> validator) {
        validators.add(validator);
    }

    @Override
    public ValidationResult validate(T input) {
        List<ValidationError> errors = validators.stream()
            .map(validator -> validator.validate(input))
            .filter(result -> result.isInvalid())
            .flatMap(result -> result.getErrors().stream())
            .collect(Collectors.toList());

        return errors.isEmpty() ? ValidationResult.valid() :
            ValidationResult.invalid(errors);
    }
}
