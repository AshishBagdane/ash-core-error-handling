package dev.ash.core.eh.exceptions;

import dev.ash.core.eh.dto.DeveloperErrorMessage;
import dev.ash.core.eh.dto.ErrorMessage;
import dev.ash.core.eh.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource is locked or in use. Used when concurrent access or modification is not allowed.
 */
@ResponseStatus(HttpStatus.LOCKED)
public final class ResourceLockedException extends BusinessException {

  public ResourceLockedException(final ErrorMessage errorMessage) {
    super(errorMessage);
  }

  public ResourceLockedException(final ErrorMessage errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }

  /**
   * Factory method for locked resource errors
   *
   * @param resourceType Type of the resource (e.g., "Document", "Account")
   * @param resourceId   Identifier of the resource
   * @return ResourceLockedException with formatted error message
   */
  public static ResourceLockedException createError(String resourceType, String resourceId) {
    return new ResourceLockedException(
        new ErrorMessage(ErrorCode.HTTP_RESOURCE_LOCKED)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_RESOURCE_LOCKED,
                "%s with ID '%s' is currently locked or in use".formatted(resourceType, resourceId)
            ))
    );
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.LOCKED;
  }

  /**
   * Factory method for timeout-based locked resource errors
   *
   * @param resourceType Type of the resource
   * @param resourceId   Identifier of the resource
   * @param lockTimeout  Duration after which the lock will expire
   * @return ResourceLockedException with formatted error message including timeout
   */
  public static ResourceLockedException createErrorWithTimeout(
      String resourceType,
      String resourceId,
      long lockTimeout) {
    return new ResourceLockedException(
        new ErrorMessage(ErrorCode.HTTP_RESOURCE_LOCKED)
            .addDeveloperMessage(new DeveloperErrorMessage(
                ErrorCode.HTTP_RESOURCE_LOCKED,
                """
                    %s with ID '%s' is currently locked. \
                    Lock will expire in %d seconds""".formatted(resourceType, resourceId, lockTimeout)
            ))
    );
  }
}