package com.playtech.bankteldrassil.validation.type;

import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.common.EventMessages;

import java.util.Optional;

/**
 * Validates the transaction type against predefined criteria.
 * Ensures that a transaction type is not null, empty, and is a recognized type.
 */
public class TypeValidator {

    private TypeValidator() {
    }

    public static Optional<String> validateType(Transaction transaction) {
        if (isTypeAbsent(transaction.getType())) {
            return Optional.of(EventMessages.TRANSACTION_FAILED_MISSING_OR_EMPTY_TRANSACTION_TYPE);
        }
        if (!isKnownTransactionType(transaction.getType())) {
            return Optional.of(EventMessages.TRANSACTION_FAILED_INVALID_TYPE.formatted(transaction.getType()));
        }
        return Optional.empty();
    }

    private static boolean isTypeAbsent(TransactionType type) {
        return type == null || type.toString().trim().isEmpty();
    }

    private static boolean isKnownTransactionType(TransactionType type) {
        for (TransactionType t : TransactionType.values()) {
            if (t == type) {
                return true;
            }
        }
        return false;
    }
}
