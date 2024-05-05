package com.playtech.bankteldrassil.validation;

import java.math.BigDecimal;

public class AmountValidator {

    private AmountValidator() {
    }

    public static boolean isAmountValid(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
