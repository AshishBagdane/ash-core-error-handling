package dev.ash.core.eh.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the different categories of error codes.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCategory {
  HTTP(400, 599),
  VALIDATION(1000, 1999),
  BUSINESS(2000, 2999),
  SECURITY(3000, 3999),
  DATA(4000, 4999),
  INTEGRATION(5000, 5999),
  SYSTEM(9000, 9999);

  private final int minCode;
  private final int maxCode;

  public boolean contains(int code) {
    return code >= minCode && code <= maxCode;
  }
}