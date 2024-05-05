package com.playtech.bankteldrassil.validation.type.deposit;

import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.validation.type.TypeSpecificValidator;
import com.playtech.bankteldrassil.service.user.UserService;

public class DepositValidator implements TypeSpecificValidator {

    private final UserService userService;

    public DepositValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int validate(Transaction transaction, User user) {
        // Validate if the deposit amount is within the defined limits
        return userService.amountInWithinLimits(transaction.getAmount(), user.getDepositMin(), user.getDepositMax());
    }
}
