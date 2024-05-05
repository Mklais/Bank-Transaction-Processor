package com.playtech.bankteldrassil.validation.identification;

import java.util.HashSet;

public class IdentificationProcessor {

    private static final HashSet<String> processedTransactionIds = new HashSet<>();

    private IdentificationProcessor() {
    }

    public static boolean isTransactionProcessed(String transactionId) {
        return processedTransactionIds.contains(transactionId);
    }

    public static void markTransactionAsProcessed(String transactionId) {
        processedTransactionIds.add(transactionId);
    }
}
