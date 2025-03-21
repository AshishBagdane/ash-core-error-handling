package com.ashishbagdane.lib.eh.exception.operation;

import com.ashishbagdane.lib.base.eh.core.ErrorCode;

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when a batch operation partially or completely fails.
 */
public class BatchOperationException extends OperationException {

    public BatchOperationException(String operation, List<String> failedItems, Map<String, String> errors) {
        super(
            ErrorCode.BUSINESS_INVALID_OPERATION,
            String.format("Batch operation '%s' failed for %d items", operation, failedItems.size()),
            createErrorContext()
                .attribute("operation", operation)
                .attribute("failedItems", failedItems)
                .attribute("itemErrors", errors)
                .attribute("totalFailed", failedItems.size())
                .build()
        );
    }

    public BatchOperationException(String operation, int totalItems, int failedCount, Map<String, String> errors) {
        super(
            ErrorCode.BUSINESS_INVALID_OPERATION,
            String.format("Batch operation '%s' failed: %d/%d items failed",
                          operation, failedCount, totalItems),
            createErrorContext()
                .attribute("operation", operation)
                .attribute("totalItems", totalItems)
                .attribute("failedCount", failedCount)
                .attribute("successCount", totalItems - failedCount)
                .attribute("errors", errors)
                .build()
        );
    }
}
