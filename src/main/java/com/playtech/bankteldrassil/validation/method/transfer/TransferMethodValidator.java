package com.playtech.bankteldrassil.validation.method.transfer;

import com.playtech.bankteldrassil.model.AccountInfo;
import com.playtech.bankteldrassil.model.Transaction;
import com.playtech.bankteldrassil.model.User;
import com.playtech.bankteldrassil.validation.method.MethodSpecificValidator;
import com.playtech.bankteldrassil.validation.CountryCodeValidator;
import com.playtech.bankteldrassil.validation.IbanValidator;
import com.playtech.bankteldrassil.common.EventMessages;

import java.util.Optional;

public class TransferMethodValidator implements MethodSpecificValidator {

    @Override
    public Optional<String> validate(Transaction transaction,  User user, AccountInfo accountInfo) {
        if (!IbanValidator.validate(transaction.getAccountNumber())) {
            return Optional.of(
                    EventMessages.TRANSACTION_FAILED_IBAN_INVALID.formatted(transaction.getAccountNumber())
            );
        }

        if (!CountryCodeValidator.transferCountryValid(user, accountInfo)) {
            return Optional.of(
                    EventMessages.TRANSACTION_FAILED_INVALID_ACCOUNT_COUNTRY.formatted(accountInfo.getCountry(), user.getCountry())
            );
        }

        return Optional.empty();
    }
}