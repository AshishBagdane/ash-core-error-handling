package com.ashishbagdane.lib.eh.exception.resource;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;
import com.ashishbagdane.lib.base.eh.core.ErrorContext;
import com.ashishbagdane.lib.base.eh.exception.base.AbstractApplicationException;

/**
 * Base class for resource-related exceptions in the application.
 */
public abstract class ResourceException extends AbstractApplicationException {

    protected ResourceException(ErrorCode errorCode, ErrorContext errorContext) {
        super(errorCode, errorContext);
    }

    protected ResourceException(ErrorCode errorCode, String message, ErrorContext errorContext) {
        super(errorCode, message, errorContext);
    }

    protected ResourceException(ErrorCode errorCode, Throwable cause, ErrorContext errorContext) {
        super(errorCode, cause, errorContext);
    }
}
