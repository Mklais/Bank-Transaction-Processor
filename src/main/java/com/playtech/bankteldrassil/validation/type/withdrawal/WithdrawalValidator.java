package com.playtech.bankteldrassil.validation.type.withdrawal;

import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.validation.type.TypeSpecificValidator;
import com.playtech.bankteldrassil.service.user.UserService;

public class WithdrawalValidator implements TypeSpecificValidator {

    private final UserService userService;

    public WithdrawalValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int validate(Transaction transaction, User user) {
        // Validate if the withdrawal amount is within the defined limits
        return userService.amountInWithinLimits(transaction.getAmount(), user.getWithdrawMin(), user.getWithdrawMax());
    }
}
