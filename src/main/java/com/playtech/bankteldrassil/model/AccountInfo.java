package com.playtech.bankteldrassil.model;

import com.playtech.bankteldrassil.enums.BinType;
import com.playtech.bankteldrassil.enums.TransactionMethodType;
import com.playtech.bankteldrassil.enums.TransactionType;

public class AccountInfo {
    private String country;

    private BinType binType;

    private TransactionMethodType transactionMethodType;

    private TransactionType transactionType;

    private boolean previousDepositSuccessful = false;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BinType getBinType() {
        return binType;
    }

    public void setBinType(BinType binType) {
        this.binType = binType;
    }

    public TransactionMethodType getTransactionMethodType() {
        return transactionMethodType;
    }

    public void setTransactionMethodType(TransactionMethodType transactionMethodType) {
        this.transactionMethodType = transactionMethodType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isPreviousDepositSuccessful() {
        return previousDepositSuccessful;
    }

    public void setPreviousDepositSuccessful(boolean previousDepositSuccessful) {
        this.previousDepositSuccessful = previousDepositSuccessful;
    }
}
