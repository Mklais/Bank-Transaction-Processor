package com.playtech.bankteldrassil.validation.method;

import com.playtech.bankteldrassil.enums.TransactionMethodType;
import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.validation.method.card.CardMethodSpecificValidator;
import com.playtech.bankteldrassil.validation.method.transfer.TransferMethodValidator;

import java.util.Map;
import java.util.Optional;

import static com.playtech.bankteldrassil.enums.TransactionMethodType.CARD;
import static com.playtech.bankteldrassil.enums.TransactionMethodType.TRANSFER;

public class MethodValidationDispatcher {

    private final Map<TransactionMethodType, MethodSpecificValidator> validatorMap;

    public MethodValidationDispatcher() {
        this.validatorMap = Map.of(
                CARD, new CardMethodSpecificValidator(),
                TRANSFER, new TransferMethodValidator()
        );
    }

    public Optional<String> validateMethodSpecificTransaction(Transaction transaction, User user, AccountInfo accountInfo) {
        MethodSpecificValidator validator = validatorMap.get(TransactionMethodType.valueOf(transaction.getMethod()));

        if (validator != null) {
            return validator.validate(transaction, user, accountInfo);
        }

        return Optional.empty();
    }
}
