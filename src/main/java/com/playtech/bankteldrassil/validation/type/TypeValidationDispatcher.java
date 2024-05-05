package com.playtech.bankteldrassil.validation.type;

import com.playtech.bankteldrassil.enums.TransactionType;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.service.event.EventService;
import com.playtech.bankteldrassil.service.event.EventServiceImpl;
import com.playtech.bankteldrassil.validation.type.deposit.DepositValidator;
import com.playtech.bankteldrassil.validation.type.withdrawal.WithdrawalValidator;
import com.playtech.bankteldrassil.service.user.UserService;

import java.util.Map;
import java.util.Optional;

public class TypeValidationDispatcher {

    private final Map<TransactionType, TypeSpecificValidator> validatorMap;

    private final EventService eventService;

    private final UserService userService;

    public TypeValidationDispatcher(UserService userService) {
        this.eventService = new EventServiceImpl();
        this.validatorMap = Map.of(
                TransactionType.DEPOSIT, new DepositValidator(userService),
                TransactionType.WITHDRAW, new WithdrawalValidator(userService)
        );
        this.userService = userService;
    }

    public Optional<String> validateTypeSpecificTransaction(Transaction transaction, User user) {
        int outOfLimit;

        TypeSpecificValidator validator = validatorMap.get(transaction.getType());
        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            outOfLimit = validator.validate(transaction, user);
            if (outOfLimit != 0) {
                return Optional.of(
                        eventService.addDepositAmountInvalid(outOfLimit, transaction.getAmount(), user)
                );
            }
        } else if (transaction.getType().equals(TransactionType.WITHDRAW)) {
            outOfLimit = validator.validate(transaction, user);
            if (outOfLimit != 0) {
                return Optional.of(
                        eventService.addWithdrawAmountInvalid(outOfLimit, transaction.getAmount(), user)
                );
            }
            if (!userService.isSufficientBalanceForWithdrawal(transaction, user)) {
                return Optional.of(
                        eventService.addInvalidBalanceForWithdrawalTypeMessage(transaction.getAmount(), user.getBalance())
                );
            }
        }
        return Optional.empty();
    }
}
