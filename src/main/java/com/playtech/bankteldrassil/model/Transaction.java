package com.playtech.bankteldrassil.model;

import com.playtech.bankteldrassil.enums.TransactionType;

import java.math.BigDecimal;

public class Transaction {
    private String transactionId;
    private String userId;
    private TransactionType type;
    private BigDecimal amount;
    private String method;
    private String accountNumber;

    private boolean successful = true;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setFailed() {
        successful = false;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", successful=" + successful +
                '}';
    }
}
