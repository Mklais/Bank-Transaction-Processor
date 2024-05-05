package com.playtech.bankteldrassil.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String username;
    private BigDecimal balance;
    private String country;
    private boolean frozen;
    private BigDecimal depositMin;
    private BigDecimal depositMax;
    private BigDecimal withdrawMin;
    private BigDecimal withdrawMax;

    private Map<String, AccountInfo> userAccounts = new HashMap<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public BigDecimal getDepositMin() {
        return depositMin;
    }

    public void setDepositMin(BigDecimal depositMin) {
        this.depositMin = depositMin;
    }

    public BigDecimal getDepositMax() {
        return depositMax;
    }

    public void setDepositMax(BigDecimal depositMax) {
        this.depositMax = depositMax;
    }

    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public Map<String, AccountInfo> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Map<String, AccountInfo> userAccounts) {
        this.userAccounts = userAccounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", country='" + country + '\'' +
                ", frozen=" + frozen +
                ", depositMin=" + depositMin +
                ", depositMax=" + depositMax +
                ", withdrawMin=" + withdrawMin +
                ", withdrawMax=" + withdrawMax +
                ", userAccounts=" + userAccounts +
                '}';
    }
}
