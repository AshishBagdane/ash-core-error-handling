package com.ashishbagdane.lib.eh.exception.validation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.base.eh.core.ErrorContext;
import com.ashishbagdane.lib.base.eh.exception.base.AbstractApplicationException;

/**
 * Base class for validation-related exceptions in the application.
 */
public abstract class ValidationException extends AbstractApplicationException {

    protected ValidationException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected ValidationException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected ValidationException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
