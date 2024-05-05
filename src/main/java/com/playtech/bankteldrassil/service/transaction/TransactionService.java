package com.playtech.bankteldrassil.service.transaction;

import com.playtech.bankteldrassil.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions();
}
