package com.playtech.bankteldrassil.csv;


import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

public class TransactionCsvParser implements CsvLineParser<Transaction> {

    @Override
    public Optional<Transaction> parseCsvLine(String line, String expectedCsvHeader) {
        try {
            String[] parts = line.split(",");

            Transaction transaction = new Transaction();
            transaction.setTransactionId(parts[0]);
            transaction.setUserId(parts[1]);
            transaction.setType(TransactionType.valueOf(parts[2].toUpperCase()));
            transaction.setAmount(new BigDecimal(parts[3]));
            transaction.setMethod(parts[4].toUpperCase());
            transaction.setAccountNumber(parts[5]);

            return Optional.of(transaction);
        } catch (RuntimeException e) {
            System.out.println("Failed to parse line: " + line + " reason: " + e.getMessage());
            return Optional.empty();
        }
    }
}
